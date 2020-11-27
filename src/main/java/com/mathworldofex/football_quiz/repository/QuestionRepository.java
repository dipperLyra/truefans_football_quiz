package com.mathworldofex.football_quiz.repository;

import com.mathworldofex.football_quiz.enity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
}
