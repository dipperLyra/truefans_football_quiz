package com.mathworldofex.football_quiz.model.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaystackDataResponse {
    private  int id;
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
    private Object metadata;
    private  Object log;
    private String fees;
    @JsonProperty("fees_split")
    private String feesSplit;
    private Object authorization;
    private Object customer;
    private String plan;
    private Object split;
    @JsonProperty("order_id")
    private String orderId;
    @JsonProperty("requested_amount")
    private String requestedAmount;
    @JsonProperty("post_transaction_data")
    private String posTransactionData;
    @JsonProperty("transaction_date")
    private String transactionDate;
    @JsonProperty("plan_object")
    private Object planObject;
    private Object subaccount;

}

