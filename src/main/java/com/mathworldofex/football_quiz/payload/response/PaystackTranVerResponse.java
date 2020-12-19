package com.mathworldofex.football_quiz.payload.response;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

@Getter
@Setter
public class PaystackTranVerResponse {

    private Boolean status;
    private String message;
    private HashMap<String, PaystackDataResponse> data;

}
