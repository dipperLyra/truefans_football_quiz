package com.mathworldofex.football_quiz.controller;

import com.mathworldofex.football_quiz.services.rest.RestClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
public class PaymentController {

    RestClient restClient;

    @Value("${paystack.verification.url}")
    private String paymentVerificationUrl;

    public PaymentController(RestClient restClient) {
        this.restClient = restClient;
    }

    @GetMapping("/verify_transaction")
    public ResponseEntity<String> verifyPayment(@RequestParam String reference) {
        return restClient.getRequest(paymentVerificationUrl + reference);
    }

    @GetMapping("/payment")
    public String checkLogin(Principal principal) {
        if (principal.getName().isEmpty()) {
            return "user/sign-in";
        }
        return "payment/paystack-pop-up.html";
    }
}
