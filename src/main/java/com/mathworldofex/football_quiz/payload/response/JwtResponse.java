package com.mathworldofex.football_quiz.payload.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class JwtResponse {
    private String token;
    private String type;
    private Long id;
    private String username;
    private String email;
    private List<String> roles;


    public JwtResponse(String token, String type, Long id, String username, String email, List<String> roles) {
        this.token = token;
        this.type = type;
        this.id = id;
        this.username = username;
        this.email = email;
        this.roles = roles;
    }

    public JwtResponse(String token, Long id, String username, String email, List<String>  roles) {
        this.token = token;
        this.id = id;
        this.username = username;
        this.email = email;
        this.roles = roles;
    }
}
