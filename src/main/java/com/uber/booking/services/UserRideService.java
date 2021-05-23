package com.uber.booking.services;

import com.uber.booking.entities.RideEntity;
import com.uber.booking.pojo.request.UserRideRequest;
import com.uber.booking.pojo.response.RideDetailsResponse;
import com.uber.booking.repositories.RideRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Component
public class UserRideService {

    private static final String REQUESTED_STATUS = "REQUESTED";
    private static final String COMPLETED = "COMPLETED";
    private static final String IN_PROGRESS_STATUS = "IN_PROGRESS";
    private static final String CANCELED = "CANCELED";


    @Autowired
    private RideRepository rideRepository;

    public void requestRide(UserRideRequest userRideRequest) {

        List<String> status = new ArrayList<>();
        status.add(COMPLETED);
        status.add(REQUESTED_STATUS);
        status.add(IN_PROGRESS_STATUS);
        Optional<RideEntity> rideEntityOptional =
                rideRepository.findFirstByRiderIdAndStatusInAndDeleted(userRideRequest.getUserId(), status, false);
        if(rideEntityOptional.isPresent()){
            throw new RuntimeException("already active ride");
        }
        RideEntity rideEntity = new RideEntity();
        rideEntity.setRiderId(userRideRequest.getUserId());
        rideEntity.setNearestLandMark(userRideRequest.getStartLandMark());
        rideEntity.setPinCode(userRideRequest.getPinCode());
        rideEntity.setRiderPhoneNumber(userRideRequest.getPhoneNumber());
        rideEntity.setVehicleType(userRideRequest.getVehicleType());
        rideEntity.setDeleted(false);
        rideEntity.setStatus(REQUESTED_STATUS);
        rideRepository.save(rideEntity);
    }

    public void cancelRide(Long rideId) {
        Optional<RideEntity> rideEntityOptional =
                rideRepository.findFirstByIdAndStatusAndDeleted(rideId, REQUESTED_STATUS, false);
        if(!rideEntityOptional.isPresent()){
            throw new RuntimeException("no active ride");
        }
        RideEntity rideEntity = rideEntityOptional.get();
        rideEntity.setStatus(CANCELED);
        rideRepository.save(rideEntity);

    }

    public RideDetailsResponse getRideDetails(Long rideId) {

        Optional<RideEntity> rideEntityOptional = rideRepository.findFirstByIdAndDeleted(rideId, false);
        if(!rideEntityOptional.isPresent()){
            throw new RuntimeException("no ride present");
        }
        RideEntity rideEntity = rideEntityOptional.get();
        RideDetailsResponse rideDetailsResponse = new RideDetailsResponse();
        rideDetailsResponse.setRideId(rideId);
        rideDetailsResponse.setRideStatus(rideEntity.getStatus());
        rideDetailsResponse.setPinCode(rideEntity.getPinCode());
        rideDetailsResponse.setStartLandMark(rideEntity.getNearestLandMark());
        return rideDetailsResponse;
    }
}
