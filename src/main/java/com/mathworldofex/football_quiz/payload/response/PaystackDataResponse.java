package com.mathworldofex.football_quiz.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.Map;

@Getter
public class PaystackDataResponse {
    private boolean id;
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
    private String metadata;
    private Map<String, PaystackLogResponse> log;
}

