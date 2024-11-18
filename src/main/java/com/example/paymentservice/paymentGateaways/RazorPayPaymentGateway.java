package com.example.paymentservice.paymentGateaways;

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
    public String generatePaymentLink(Long orderId, Long amount) throws RazorpayException {
        try {
//            Order Details
            JSONObject paymentLinkRequest = new JSONObject();
            paymentLinkRequest.put("amount",amount);
            paymentLinkRequest.put("currency","INR");
//        paymentLinkRequest.put("accept_partial",true);
//        paymentLinkRequest.put("first_min_partial_amount",100);
            paymentLinkRequest.put("expire_by",1732293753);
            paymentLinkRequest.put("reference_id",orderId.toString());
            paymentLinkRequest.put("description","Payment for policy no 00001");

//        Customer Details:::
            JSONObject customer = new JSONObject();
            customer.put("name","Ridhim");
            customer.put("contact","+916239818765");
            customer.put("email","ridhimraizada.rr@gmail.com");
            paymentLinkRequest.put("customer",customer);

//        Notification Details::::
            JSONObject notify = new JSONObject();
//        notify.put("sms",true);
//        notify.put("email",true);
            paymentLinkRequest.put("notify",notify);
//        paymentLinkRequest.put("reminder_enable",true);
//        JSONObject notes = new JSONObject();
//        notes.put("policy_name","Jeevan Bima");
//        paymentLinkRequest.put("notes",notes);
            paymentLinkRequest.put("callback_url","https://paymentservice.free.beeceptor.com");
            paymentLinkRequest.put("callback_method","get");

            PaymentLink payment = razorpayClient.paymentLink.create(paymentLinkRequest);
            return  payment.toString();
        }catch (Exception e){
            throw new RazorpayException(e.getMessage());
        }
    }
}
