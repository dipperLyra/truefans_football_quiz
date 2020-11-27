package com.mathworldofex.football_quiz.payload.requests;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AnswerRequest {
    private String answer;
    private String question;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;

    public AnswerRequest(String optionA, String optionB, String optionC, String optionD) {
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.optionD = optionD;
    }
}
