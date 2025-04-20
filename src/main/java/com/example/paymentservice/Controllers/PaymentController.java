package com.example.paymentservice.Controllers;

import com.example.paymentservice.Common.AuthCommon;
import com.example.paymentservice.DTO.PaymentRequestDto;
import com.example.paymentservice.DTO.PaymentResponseDto;
import com.example.paymentservice.DTO.UserDto;
import com.example.paymentservice.Services.paymentService;
import org.apache.catalina.User;
import org.springframework.boot.task.ThreadPoolTaskSchedulerBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/payments")
public class PaymentController {
    private final ThreadPoolTaskSchedulerBuilder threadPoolTaskSchedulerBuilder;
    private final AuthCommon authCommon;
    private paymentService service;

    public PaymentController(paymentService service, ThreadPoolTaskSchedulerBuilder threadPoolTaskSchedulerBuilder, AuthCommon authCommon) {
        this.service = service;
        this.threadPoolTaskSchedulerBuilder = threadPoolTaskSchedulerBuilder;
        this.authCommon = authCommon;
    }

    @PostMapping("/")
    public PaymentResponseDto initiatePayment(@RequestBody PaymentRequestDto requestDto, @RequestHeader("Authorization") String auth) throws Exception {
        UserDto user = authCommon.validate(auth);
        if (user == null) {
            throw new Exception("Invalid Authorization");
        }
        PaymentResponseDto responseDto;
        try {
            responseDto = service.createPaymentLink(requestDto.getOrderId(), requestDto.getAmount(), user.getName(), user.getEmail());
        } catch (Exception e) {
            System.out.println(e);
            throw new Exception(e.getMessage());
        }
        return responseDto;
    }

    @PostMapping("/callback")
    public ResponseEntity<String> handlePaymentCallback(
            @RequestBody String payload
    ) {
        try {
            System.out.println("✅ Webhook received: " + payload);

            // Temporarily skip signature verification
            service.processWebhook(payload);

            return ResponseEntity.ok("Webhook processed");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error");
        }
    };
}



