package com.mathworldofex.football_quiz.model.payload.requests;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class QuizSetUpRequest {

    private String category;
    private String subCategory;
}
