package com.mathworldofex.football_quiz.payload.requests;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SubmitAnswerRequest {
    private String answer;
    private String questionId;
}
