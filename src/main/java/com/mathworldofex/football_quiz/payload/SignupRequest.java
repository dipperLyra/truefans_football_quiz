package com.mathworldofex.football_quiz.payload;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
public class SignupRequest {

    private String firstname;
    private String lastname;
    private String username;
    private String email;
    private String password;

}
