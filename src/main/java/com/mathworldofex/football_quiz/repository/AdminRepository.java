package com.mathworldofex.football_quiz.repository;

import com.mathworldofex.football_quiz.enity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
}