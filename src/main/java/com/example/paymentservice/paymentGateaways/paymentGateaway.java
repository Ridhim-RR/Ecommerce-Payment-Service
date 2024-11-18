package com.example.paymentservice.paymentGateaways;

public interface paymentGateaway {
    String generatePaymentLink(Long orderId, int amount);
}
