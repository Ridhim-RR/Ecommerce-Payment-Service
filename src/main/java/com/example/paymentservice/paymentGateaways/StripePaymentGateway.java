package com.example.paymentservice.paymentGateaways;

import com.example.paymentservice.DTO.PaymentResponseDto;

public class StripePaymentGateway implements paymentGateway {

    public PaymentResponseDto generatePaymentLink(Long orderId, Long amount, String userName, String userEmail){
        return  null;
    }
}
