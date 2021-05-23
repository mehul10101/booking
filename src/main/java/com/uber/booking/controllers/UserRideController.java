package com.uber.booking.controllers;

import com.uber.booking.pojo.request.UserRideRequest;
import com.uber.booking.pojo.response.RideDetailsResponse;
import com.uber.booking.services.UserRideService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping(value = "/user/booking")
@Slf4j
@AllArgsConstructor
public class UserRideController {

    @Autowired
    private final UserRideService userRideService;

    @PostMapping(value = "/requestRide")
    public void requestRide(@RequestBody UserRideRequest userRideRequest) throws IOException {

        userRideService.requestRide(userRideRequest);
    }

    // cancel booking
    @PostMapping(value = "/cancelRide")
    public void cancelRide(@Param("rideId") Long rideId) throws IOException {

        userRideService.cancelRide(rideId);
    }

    //getRide
    @GetMapping(value = "/rideDetails")
    public RideDetailsResponse getRideDetails(@Param("rideId") Long rideId) throws IOException {

        return userRideService.getRideDetails(rideId);
    }

}
