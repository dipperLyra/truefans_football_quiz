package com.mathworldofex.football_quiz.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private User user;
    private String domain;
    private String status;
    private String reference;
    private long amount;
    private String message;
    @JsonProperty("gateway_response")
    private String gatewayResponse;
    @JsonProperty("paid_at")
    private String paidAt;
    @JsonProperty("created_at")
    private String createdAt;
    private String channel;
    private String currency;
    @JsonProperty("ip_address")
    private String ipAddress;

    public Transaction(User user, String domain, String status,
                       String reference, long amount, String message,
                       String gatewayResponse, String paidAt, String createdAt,
                       String channel, String currency, String ipAddress)
    {
        this.user = user;
        this.domain = domain;
        this.status = status;
        this.reference = reference;
        this.amount = amount;
        this.message = message;
        this.gatewayResponse = gatewayResponse;
        this.paidAt = paidAt;
        this.createdAt = createdAt;
        this.channel = channel;
        this.currency = currency;
        this.ipAddress = ipAddress;
    }
}
