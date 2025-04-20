package com.example.paymentservice.DTO;

import lombok.Data;

@Data
public class PaymentResponseDto {
    private String paymentLink;
    private String orderId;
    private Long amount;
    private String currency;
    private String status;
}
