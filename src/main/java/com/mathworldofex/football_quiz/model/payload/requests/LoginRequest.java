package com.mathworldofex.football_quiz.model.payload.requests;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginRequest {
    private String username;
    private String password;
    private String role;
}
