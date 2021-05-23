package com.uber.booking.services;

import com.uber.booking.entities.RideEntity;
import com.uber.booking.pojo.request.BookingDetailsRequest;
import com.uber.booking.pojo.response.BookingDetailsResponse;
import com.uber.booking.repositories.RideRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Component
public class DriverRideService {

    @Autowired
    private RideRepository rideRepository;

    private static final String REQUESTED_STATUS = "REQUESTED";
    private static final String BOOKED_STATUS = "BOOKED";
    private static final String IN_PROGRESS_STATUS = "IN_PROGRESS";
    private static final String COMPLETED = "COMPLETED";


    public List<BookingDetailsResponse> getAllRides(BookingDetailsRequest bookingDetailsRequest) {
        List<RideEntity> rideEntities = rideRepository.findAllByPinCodeAndVehicleTypeAndStatusAndDeleted(
                bookingDetailsRequest.getPinCode(), bookingDetailsRequest.getVehicleType(), REQUESTED_STATUS, false);
        List<BookingDetailsResponse> bookingDetailsResponseList = new ArrayList<>();
        for(RideEntity rideEntity : rideEntities){
            BookingDetailsResponse bookingDetailsResponse = new BookingDetailsResponse();
            bookingDetailsResponse.setId(rideEntity.getId());
            bookingDetailsResponse.setStartPoint(rideEntity.getNearestLandMark());
            bookingDetailsResponseList.add(bookingDetailsResponse);
        }
        return bookingDetailsResponseList;
    }

    public BookingDetailsResponse bookRide(Long rideId, Integer driverId) {
        Optional<RideEntity> rideEntityOptional = rideRepository.findFirstByIdAndStatusAndDeleted(rideId, REQUESTED_STATUS, false);
        if(!rideEntityOptional.isPresent()){
            throw new RuntimeException("ride not present or already booked");
        }
        RideEntity rideEntity = rideEntityOptional.get();
        rideEntity.setStatus(BOOKED_STATUS);
        rideEntity.setDriverId(driverId);
        rideRepository.save(rideEntity);
        BookingDetailsResponse bookingDetailsResponse = new BookingDetailsResponse();
        bookingDetailsResponse.setId(rideEntity.getId());
        bookingDetailsResponse.setStartPoint(rideEntity.getNearestLandMark());
        bookingDetailsResponse.setPhoneNumber(rideEntity.getRiderPhoneNumber());
        return bookingDetailsResponse;
    }

    public BookingDetailsResponse cancelRide(Long rideId, Integer driverId) {
        Optional<RideEntity> rideEntityOptional = rideRepository.findFirstByIdAndStatusAndDeletedAndDriverId(rideId,
                BOOKED_STATUS, false, driverId);
        if(!rideEntityOptional.isPresent()){
            throw new RuntimeException("cannot cancel ride, not booked with this driver");
        }
        RideEntity rideEntity = rideEntityOptional.get();
        rideEntity.setStatus(REQUESTED_STATUS);
        rideEntity.setDriverId(null);
        rideRepository.save(rideEntity);
        BookingDetailsResponse bookingDetailsResponse = new BookingDetailsResponse();
        bookingDetailsResponse.setId(rideEntity.getId());
        return bookingDetailsResponse;
    }

    public void startRide(Long rideId, Integer driverId) {
        Optional<RideEntity> rideEntityOptional = rideRepository.findFirstByIdAndStatusAndDeletedAndDriverId(rideId,
                BOOKED_STATUS, false, driverId);
        if(!rideEntityOptional.isPresent()){
            throw new RuntimeException("cannot start ride, not booked with this driver");
        }
        RideEntity rideEntity = rideEntityOptional.get();
        rideEntity.setStatus(IN_PROGRESS_STATUS);
        rideRepository.save(rideEntity);
    }

    public void endRide(Long rideId, Integer driverId) {
        Optional<RideEntity> rideEntityOptional = rideRepository.findFirstByIdAndStatusAndDeletedAndDriverId(rideId,
                IN_PROGRESS_STATUS, false, driverId);
        if(!rideEntityOptional.isPresent()){
            throw new RuntimeException("cannot en ride, not started with this driver");
        }
        RideEntity rideEntity = rideEntityOptional.get();
        rideEntity.setStatus(COMPLETED);
        rideRepository.save(rideEntity);
    }
}
