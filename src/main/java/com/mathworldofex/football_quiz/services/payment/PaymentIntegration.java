package com.mathworldofex.football_quiz.services.payment;

import com.mathworldofex.football_quiz.payload.response.PaystackTranVerResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Service
public class PaymentIntegration {


    @Value("${mwe.app.jwtSecret}")
    private String paymentApiSecret;
    @Value("${paystack.verification.url}")
    private String paymentVerificationUrl;


    private ResponseEntity<PaystackTranVerResponse> postReference(String reference) {
        String URI = paymentVerificationUrl + reference;

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(Objects.requireNonNull(paymentApiSecret));
        HttpEntity<String> entity = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();

        return restTemplate.getForEntity(URI, PaystackTranVerResponse.class, entity);
    }

}
