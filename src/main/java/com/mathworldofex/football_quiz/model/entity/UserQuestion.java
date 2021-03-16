package com.mathworldofex.football_quiz.model.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


@Getter
@Setter
public class UserQuestion implements Serializable {
    private String id;
    private String question;

    public UserQuestion(String id, String question) {
        this.id = id; this.question = question;
    }
}
