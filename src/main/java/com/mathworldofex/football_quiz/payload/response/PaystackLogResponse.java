package com.mathworldofex.football_quiz.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;
import java.util.List;

public class PaystackLogResponse {

    @JsonProperty("start_time")
    private Long startTime;
    @JsonProperty("time_spent")
    private Integer timeSpent;
    private Integer attempts;
    private Integer errors;
    private Boolean success;
    private Boolean mobile;
    private Arrays input;
    private List<PaystackHistoryReponse> history;

}
