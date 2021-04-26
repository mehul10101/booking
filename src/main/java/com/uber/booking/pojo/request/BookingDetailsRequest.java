package com.uber.booking.pojo.request;

import com.sun.istack.NotNull;
import lombok.Data;

@Data
public class BookingDetailsRequest {
    @NotNull private String vehicleType;
    @NotNull private Integer pinCode;

}
