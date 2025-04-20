package com.example.paymentservice.Utils;

import com.example.paymentservice.Common.AuthCommon;
import com.example.paymentservice.DTO.OrderRequestDto;
import com.example.paymentservice.Services.paymentServiceImpl;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class PaymentEvent {
    private AuthCommon authCommon;
    private static final Logger logger = LoggerFactory.getLogger(PaymentEvent.class);

    public PaymentEvent(AuthCommon authCommon) {
        this.authCommon = authCommon;
    }
    public void handleCapturedEvent(JsonNode root){
  try{
      JsonNode payment = root.path("payload").path("payment").path("entity");

      // Try to fetch reference_id from top level
      String internalOrderId = payment.path("reference_id").asText();

      // Fallback: Try from notes if top level is missing or empty
      if (internalOrderId == null || internalOrderId.isEmpty()) {
          internalOrderId = payment.path("notes").path("reference_id").asText();
      }

      if (internalOrderId == null || internalOrderId.isEmpty()) {
          logger.error("❌ reference_id not found in webhook payload: " + payment.toPrettyString());
          return;
      }

      Long orderId = Long.parseLong(internalOrderId);
      logger.info("✅ Payment captured for Order ID: " + orderId);

      OrderRequestDto requestDto = new OrderRequestDto();
      requestDto.setOrderId(orderId);
      requestDto.setPaymentStatus("SUCCESS");

      authCommon.updateOrder(requestDto);

  }catch (Exception e){
      logger.error("Error processing payment.captured event", e);
  }
    }

    public void handleFailedEvent(JsonNode root){
        try{
            JsonNode payment = root.path("payload").path("payment").path("entity");
            String internalOrderId =  payment.path("reference_id").asText();;
            Long orderId = Long.parseLong(internalOrderId);


            logger.info("✅ Payment captured for Order ID: " + orderId);

            OrderRequestDto requestDto = new OrderRequestDto();
            requestDto.setOrderId(orderId);
            requestDto.setPaymentStatus("FAILURE");

            authCommon.updateOrder(requestDto);

        }catch (Exception e){
            logger.error("Error processing payment.captured event", e);
        }
    }
}
