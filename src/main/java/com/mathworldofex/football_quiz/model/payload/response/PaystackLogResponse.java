package com.mathworldofex.football_quiz.model.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;

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
    private Object history;

}
