package com.mathworldofex.football_quiz.repository;

import com.mathworldofex.football_quiz.enity.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScoreRepository extends JpaRepository<Score, Long> {
}
