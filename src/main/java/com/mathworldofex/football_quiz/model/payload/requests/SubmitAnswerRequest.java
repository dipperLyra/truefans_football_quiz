package com.mathworldofex.football_quiz.model.payload.requests;

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
