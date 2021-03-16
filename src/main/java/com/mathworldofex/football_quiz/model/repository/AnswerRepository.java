package com.mathworldofex.football_quiz.model.repository;

import com.mathworldofex.football_quiz.model.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {
}
