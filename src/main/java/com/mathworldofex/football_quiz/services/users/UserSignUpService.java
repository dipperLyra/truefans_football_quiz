package com.mathworldofex.football_quiz.services.users;

import com.mathworldofex.football_quiz.enity.User;
import com.mathworldofex.football_quiz.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserSignUpService {

    UserRepository userRepository;

    public UserSignUpService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String create(User user) {
        if (user != null ) {
            userRepository.save(user);
            return "User created";
        } else {
            return "User creation failed";
        }
    }
}
