package com.example.paymentservice.paymentGateaways;

import com.example.paymentservice.DTO.PaymentResponseDto;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.razorpay.Utils;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

@Component
public class RazorPayPaymentGateway implements paymentGateway {
    private RazorpayClient razorpayClient;
    public RazorPayPaymentGateway(RazorpayClient razorpayClient) {
        this.razorpayClient = razorpayClient;
    }
    @Override
    public PaymentResponseDto generatePaymentLink(Long orderId, Long amount, String userName, String userEmail) throws RazorpayException {
        try {
            JSONObject paymentLinkRequest = new JSONObject();
            paymentLinkRequest.put("amount", amount * 100); // in paise
            paymentLinkRequest.put("currency", "INR");
            long expiresBy = System.currentTimeMillis() / 1000 + 16 * 60;
            paymentLinkRequest.put("expire_by", expiresBy);

            // ✅ Setting reference_id
            paymentLinkRequest.put("reference_id", orderId.toString());

            // ✅ Also add reference_id inside notes
            JSONObject notes = new JSONObject();
            notes.put("reference_id", orderId.toString());
            paymentLinkRequest.put("notes", notes);

            paymentLinkRequest.put("description", "Payment for order " + orderId);

            JSONObject customer = new JSONObject();
            customer.put("name", userName);
            customer.put("email", userEmail);
            paymentLinkRequest.put("customer", customer);

            JSONObject notify = new JSONObject();
            notify.put("email", true);
            paymentLinkRequest.put("notify", notify);

            paymentLinkRequest.put("callback_url", "https://b875-223-178-210-249.ngrok-free.app/payments/callback");
            paymentLinkRequest.put("callback_method", "get");

            PaymentLink payment = razorpayClient.paymentLink.create(paymentLinkRequest);
            JSONObject responseJson = new JSONObject(payment.toString());

            PaymentResponseDto responseDto = new PaymentResponseDto();
            responseDto.setPaymentLink(responseJson.getString("short_url"));
            responseDto.setAmount(responseJson.getLong("amount"));
            responseDto.setCurrency(responseJson.getString("currency"));
            responseDto.setStatus(responseJson.getString("status"));

            return responseDto;

        } catch (Exception e) {
            throw new RazorpayException(e.getMessage());
        }
    }

}
