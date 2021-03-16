package com.mathworldofex.football_quiz.model.payload.requests;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class QuestionOptionRequest {
    private String question;
    private String category;
    private String category1;
    private String subCategory;
    private String subCategory1;
    private String answer;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;

}
