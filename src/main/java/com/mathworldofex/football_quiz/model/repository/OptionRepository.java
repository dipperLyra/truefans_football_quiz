package com.mathworldofex.football_quiz.model.repository;

import com.mathworldofex.football_quiz.model.entity.QuestionOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OptionRepository extends JpaRepository<QuestionOption, Long> {
}
