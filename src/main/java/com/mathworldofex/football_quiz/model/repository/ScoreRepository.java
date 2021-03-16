package com.mathworldofex.football_quiz.model.repository;

import com.mathworldofex.football_quiz.model.entity.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScoreRepository extends JpaRepository<Score, Long> {
}
