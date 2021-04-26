package com.uber.booking.pojo.response;

import lombok.Data;

@Data
public class BookingDetailsResponse {
    private Long id;
    private String phoneNumber;
    private String startPoint;
    private String response;

}
