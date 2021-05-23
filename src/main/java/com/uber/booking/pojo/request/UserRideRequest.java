package com.uber.booking.pojo.request;

import lombok.Data;

@Data
public class UserRideRequest {
    private String name;
    private Integer pinCode;
    private String vehicleType;
    private String startLandMark;
    private Integer userId;
    private String phoneNumber;

}
