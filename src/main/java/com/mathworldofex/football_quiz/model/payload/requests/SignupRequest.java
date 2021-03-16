package com.mathworldofex.football_quiz.model.payload.requests;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class SignupRequest {

    private String firstname;
    private String lastname;
    private String username;
    private String email;
    private String password;
    private String role;
}
