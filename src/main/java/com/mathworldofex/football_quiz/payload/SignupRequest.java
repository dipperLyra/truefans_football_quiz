package com.mathworldofex.football_quiz.payload;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
public class SignupRequest {

    private String username;
    private String email;
    private String password;
    private Set<String> role;

    public SignupRequest(String username, String email, String password, Set<String> role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
    }
}
