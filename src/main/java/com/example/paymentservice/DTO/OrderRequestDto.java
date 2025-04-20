package com.example.paymentservice.DTO;

import lombok.Data;

@Data
public class OrderRequestDto {
    Long orderId;
    String paymentStatus;
}
