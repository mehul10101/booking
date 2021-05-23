package com.uber.booking.pojo.response;

import lombok.Data;

@Data
public class RideDetailsResponse {
    private Long rideId;
    private Integer pinCode;
    private String startLandMark;
    private String rideStatus;
    private String driverName;

}
