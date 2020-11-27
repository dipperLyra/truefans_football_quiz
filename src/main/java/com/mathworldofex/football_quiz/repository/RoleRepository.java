package com.mathworldofex.football_quiz.repository;

import com.mathworldofex.football_quiz.enity.ERole;
import com.mathworldofex.football_quiz.enity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole username);
}
