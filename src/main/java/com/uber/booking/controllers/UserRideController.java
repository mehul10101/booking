package com.uber.booking.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping(value = "/user/booking")
@Slf4j
@AllArgsConstructor
public class UserRideController {

//    @PostMapping(value = "/requestRide")
//    public void requestRide(@RequestBody UserRideRequest userRideRequest) throws IOException {
//
//    }

    // cancel booking
    @PostMapping(value = "/cancelRide")
    public void cancelRide(@Param("rideId") Integer rideId) throws IOException {

    }

    //getRide
    @GetMapping(value = "/rideDetails")
    public void getRideDetails(@Param("rideId") Integer rideId) throws IOException {

    }

}
