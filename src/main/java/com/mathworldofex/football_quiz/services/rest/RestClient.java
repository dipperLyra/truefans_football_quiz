package com.mathworldofex.football_quiz.services.rest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import org.springframework.http.HttpHeaders;

@Service
public class RestClient {

    @Value("${mwe.app.jwtSecret}")
    private String paymentApiSecret;


    public  ResponseEntity<String> getRequest(String uri) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(paymentApiSecret);
        return restTemplate.getForEntity(uri, String.class, headers);
    }
}
