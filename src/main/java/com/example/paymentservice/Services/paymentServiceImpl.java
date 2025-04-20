package com.example.paymentservice.Services;

import com.example.paymentservice.Common.AuthCommon;
import com.example.paymentservice.DTO.OrderRequestDto;
import com.example.paymentservice.DTO.PaymentResponseDto;
import com.example.paymentservice.Utils.PaymentEvent;
import com.example.paymentservice.paymentGateaways.paymentGateway;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.razorpay.RazorpayException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class paymentServiceImpl implements paymentService {
    private paymentGateway paymentGateway;
    private AuthCommon authCommon;
    PaymentEvent paymentEvent;
    private static final Logger logger = LoggerFactory.getLogger(paymentServiceImpl.class);


    public paymentServiceImpl(paymentGateway paymentGateway, AuthCommon authCommon,PaymentEvent paymentEvent) {
        this.paymentGateway = paymentGateway;
        this.authCommon = authCommon;
        this.paymentEvent = paymentEvent;
    }

    public PaymentResponseDto createPaymentLink(Long orderId, Long amount, String userName, String userEmail) throws RazorpayException {
        System.out.println(orderId + " $$$$ORDERID");
        System.out.println(amount + " $$$$$Amount");
        System.out.println(userName + " $$$$$NAME");
        System.out.println(userEmail + " $$$$$EMail");
        try {
            return paymentGateway.generatePaymentLink(orderId, amount, userName, userEmail);
        } catch (RazorpayException e) {
            throw new RazorpayException("Failed to create Razorpay payment link", e);
        }
    }

    public String handlePaymentCallback(Map<String,String> queryParams) throws  Exception{
        try{
            String razorpayPaymentLinkId = queryParams.get("razorpay_payment_link_id");
            String razorpayPaymentId = queryParams.get("razorpay_payment_id");
            String razorpayPaymentLinkStatus = queryParams.get("razorpay_payment_link_status");
            System.out.println(razorpayPaymentId +  "  " + razorpayPaymentLinkStatus + "  " + razorpayPaymentLinkId );
        }catch (Exception e){
           throw  new Exception("ERROR");
        }
        return null;
    }

    @Override
    public void processWebhook(String payload) throws Exception {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(payload);
            String event = root.path("event").asText();

            logger.info("📩 Webhook Event Received: " + event);

            switch (event) {
                case "payment.captured":
                    paymentEvent.handleCapturedEvent(root);
                    break;

                case "payment.failed":
                    paymentEvent.handleFailedEvent(root);
                    break;

                default:
                    logger.info("🔕 Ignoring event: " + event);
            }
        }catch (Exception e) {
            logger.error("❌ Exception while processing webhook: ", e);
            // Optionally: alert or store payload to DB for retry/debug later
        }
    }

}
