package com.example.paymentservice.Common;

import com.example.paymentservice.DTO.OrderRequestDto;
import com.example.paymentservice.DTO.UserDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
public class AuthCommon {
    private RestTemplate restTemplate;
    @Value("${myapp.payment.internalToken}")
    private String internalToken; // Add this field

    public AuthCommon(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    public UserDto validate(String tokenValue){
        // This will talk to the user service to verify that the user token is valid or not.
        String token = tokenValue.replace("Bearer ", "").trim();
        String url = "http://USERSELFSERVICE/users/validate/" + token;
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(tokenValue);
        ResponseEntity<UserDto> response = restTemplate.exchange(url , HttpMethod.GET, new HttpEntity<>(headers), UserDto.class);
        if(!response.hasBody()){
       return null;
   }
       return  response.getBody();
    }

    public boolean updateOrder(OrderRequestDto requestDto){
        try {
            String url = "http://ORDERSERVICE/orders/update" ;
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + internalToken);
            headers.setContentType(MediaType.APPLICATION_JSON); // important if sending JSON
            HttpEntity<OrderRequestDto> requestEntity = new HttpEntity<>(requestDto, headers);
            ResponseEntity<Void> response = restTemplate.exchange(url , HttpMethod.PUT, requestEntity, Void.class);
            return response.getStatusCode().is2xxSuccessful();
        }catch (RestClientException e){
            e.printStackTrace();
            return false;
        }


    }
}
