package com.example.paymentservice.Services;

import com.example.paymentservice.paymentGateaways.paymentGateway;
import com.razorpay.RazorpayException;
import org.springframework.stereotype.Service;

@Service
public class paymentServiceImpl implements paymentService{
    private paymentGateway paymentGateway;

    public paymentServiceImpl(paymentGateway paymentGateway) {
        this.paymentGateway = paymentGateway;
    }
    public String createPaymentLink(Long orderId, Long amount) throws RazorpayException {
        System.out.println(orderId+ " ORDERID");
        System.out.println(amount + " Amount");
        String paymentLink = paymentGateway.generatePaymentLink(orderId, amount);
        return paymentLink;
    }
}
