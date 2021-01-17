package com.mathworldofex.football_quiz.repository;

import com.mathworldofex.football_quiz.enity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    @Query(value = "SELECT * from question order by RAND() limit 3", nativeQuery = true)
    List<Question> getRandomQuestion();

    @Query(value = "SELECT id from question", nativeQuery = true)
    List<Long> getAllId();
}
