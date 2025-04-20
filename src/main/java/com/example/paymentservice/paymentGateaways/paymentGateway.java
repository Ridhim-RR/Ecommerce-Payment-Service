package com.example.paymentservice.paymentGateaways;

import com.example.paymentservice.DTO.PaymentResponseDto;
import com.razorpay.RazorpayException;

public interface paymentGateway {
    PaymentResponseDto generatePaymentLink(Long orderId, Long amount, String  userName, String userEmail) throws RazorpayException;
}
