package com.example.paymentservice.Controllers;

import com.example.paymentservice.DTO.PaymentRequestDto;
import com.example.paymentservice.Services.paymentService;
import org.springframework.boot.task.ThreadPoolTaskSchedulerBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payments")
public class PaymentController {
    private final ThreadPoolTaskSchedulerBuilder threadPoolTaskSchedulerBuilder;
    private paymentService service;

    public PaymentController(paymentService service, ThreadPoolTaskSchedulerBuilder threadPoolTaskSchedulerBuilder) {
        this.service = service;
        this.threadPoolTaskSchedulerBuilder = threadPoolTaskSchedulerBuilder;
    }
@PostMapping("/")
    public String initiatePayment(@RequestBody PaymentRequestDto requestDto) throws Exception {
        String paymentLink = "";
    try {
      paymentLink = service.createPaymentLink(requestDto.getOrderId(), requestDto.getAmount());
    }catch(Exception e){
        System.out.println(e);
      throw new Exception(e.getMessage());
    }
     return paymentLink;
    }
}
