package com.example.paymentservice.paymentGateaways;

import com.razorpay.RazorpayException;

public interface paymentGateway {
    String generatePaymentLink(Long orderId, Long amount) throws RazorpayException;
}
