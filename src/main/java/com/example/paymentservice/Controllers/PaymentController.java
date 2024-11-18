package com.example.paymentservice.Controllers;

import com.example.paymentservice.DTO.PaymentRequestDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payments")
public class PaymentController {
@PostMapping("/")
    public String initiatePayment(@RequestBody PaymentRequestDto requestDto){
        try{

        }catch(Exception e){

        }
    }
}
