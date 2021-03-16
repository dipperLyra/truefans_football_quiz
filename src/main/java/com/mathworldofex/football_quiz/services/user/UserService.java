package com.mathworldofex.football_quiz.services.user;

import com.mathworldofex.football_quiz.model.entity.ERole;
import com.mathworldofex.football_quiz.model.entity.Role;
import com.mathworldofex.football_quiz.model.repository.RoleRepository;
import com.mathworldofex.football_quiz.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;

    public void updateUserRole(String username) {
        userRepository.findByUsername(username).ifPresent(user -> {
            Role role = roleRepository.findByName(ERole.ROLE_PAID_USER)
                    .orElse(roleRepository.save(new Role(ERole.ROLE_PAID_USER)));
            user.setRole(role);
            userRepository.save(user);

        });
    }
}
