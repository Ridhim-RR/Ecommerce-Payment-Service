package com.example.paymentservice.DTO;

import lombok.Data;

@Data
public class PaymentRequestDto {
    private long orderId;
    private int amount;
}
