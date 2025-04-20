package com.example.paymentservice.Services;

import com.example.paymentservice.DTO.PaymentResponseDto;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface paymentService {
public PaymentResponseDto createPaymentLink(Long orderId, Long amount, String name, String email) throws Exception;
public String handlePaymentCallback(Map<String,String> payload) throws  Exception;
public void processWebhook(String payload) throws Exception;
}
