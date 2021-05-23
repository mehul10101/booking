package com.uber.booking.controllers;

import com.sun.istack.NotNull;
import com.uber.booking.pojo.request.BookingDetailsRequest;
import com.uber.booking.pojo.response.BookingDetailsResponse;
import com.uber.booking.services.DriverRideService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "/driver/booking")
@Slf4j
@AllArgsConstructor
public class DriverRideController {

    @Autowired
    private final DriverRideService driverRideService;

    @GetMapping(value = "/requestsNearBy")
    public List<BookingDetailsResponse> getBookingDetails(@RequestBody BookingDetailsRequest bookingDetailsRequest) {
        return driverRideService.getAllRides(bookingDetailsRequest);
    }


    @PostMapping(value = "/bookRide")
    public BookingDetailsResponse bookRide(@NotNull @Param("rideId") Long rideId, @Param("driverId") Integer driverId) throws IOException {
        return driverRideService.bookRide(rideId, driverId);
    }


    @PutMapping(value = "/cancelRide")
    public BookingDetailsResponse cancelRide(@Param("rideId") Long rideId, @Param("driverId") Integer driverId) throws IOException {
        return driverRideService.cancelRide(rideId, driverId);
    }

    @PutMapping(value = "/startRide")
    public void startRide(@Param("rideId") Long rideId, @Param("driverId") Integer driverId) throws IOException {
        driverRideService.startRide(rideId, driverId);
    }

    /**
     * This api will change the status of the ride to COMPLETED
     * @param rideId - ride which has to be completed
     * @throws IOException
     */
    @PutMapping(value = "/endRide")
    public void endRide(@Param("rideId") Long rideId, @Param("driverId") Integer driverId) throws IOException {
        driverRideService.endRide(rideId, driverId);
    }



}
