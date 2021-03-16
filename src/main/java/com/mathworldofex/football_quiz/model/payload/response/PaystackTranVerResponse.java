package com.mathworldofex.football_quiz.model.payload.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaystackTranVerResponse {

    private Boolean status;
    private String message;
    private PaystackDataResponse data;
}
