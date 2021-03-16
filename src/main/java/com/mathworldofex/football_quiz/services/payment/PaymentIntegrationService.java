package com.mathworldofex.football_quiz.services.payment;

import com.mathworldofex.football_quiz.model.entity.Transaction;
import com.mathworldofex.football_quiz.model.entity.User;
import com.mathworldofex.football_quiz.model.payload.response.PaystackDataResponse;
import com.mathworldofex.football_quiz.model.payload.response.PaystackTranVerResponse;
import com.mathworldofex.football_quiz.model.repository.TransactionRepository;
import com.mathworldofex.football_quiz.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;

@Service
public class PaymentIntegrationService {
    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    UserRepository userRepository;

    public boolean verifyPayment(String reference, String username) throws URISyntaxException {
        HttpHeaders headers = new HttpHeaders();

        headers.add("Authorization", "Bearer sk_test_a1031c3904f6d2a3c288f5972f9f3d978275b79e");
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        RestTemplate template = new RestTemplate();
        URI paymentUrl = new URI("https://api.paystack.co/transaction/verify/" + reference);
        System.out.println("### Transaction Reference: " + reference);
        ResponseEntity<PaystackTranVerResponse> responseEntity = template.exchange(
                paymentUrl,
                HttpMethod.GET,
                requestEntity,
                PaystackTranVerResponse.class
        );
        boolean transactionVerified = Objects.requireNonNull(responseEntity.getBody()).getData().getStatus().equalsIgnoreCase("success");
        PaystackDataResponse transactionResponse = responseEntity.getBody().getData();
        this.saveUserPayment(username, transactionResponse);
        return transactionVerified;
    }

    public void saveUserPayment(String username, PaystackDataResponse pmd) {
        User user = userRepository.findByUsername(username).isPresent() ? userRepository.findByUsername(username).get() : new User();

        Transaction transaction = new Transaction(user, pmd.getDomain(), pmd.getStatus(),
                pmd.getReference(), pmd.getAmount(), pmd.getMessage(),
                pmd.getGatewayResponse(), pmd.getPaidAt(), pmd.getCreatedAt(),
                pmd.getChannel(), pmd.getChannel(), pmd.getIpAddress());

        try {
            transactionRepository.save(transaction);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
