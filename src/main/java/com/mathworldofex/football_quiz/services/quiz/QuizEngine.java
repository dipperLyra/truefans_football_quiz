package com.mathworldofex.football_quiz.services.quiz;

import com.mathworldofex.football_quiz.enity.Question;
import com.mathworldofex.football_quiz.repository.AnswerRepository;
import com.mathworldofex.football_quiz.repository.OptionRepository;
import com.mathworldofex.football_quiz.repository.QuestionRepository;
import com.mathworldofex.football_quiz.repository.ScoreRepository;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class QuizEngine {

    QuestionRepository questionRepository;
    AnswerRepository answerRepository;
    ScoreRepository scoreRepository;
    OptionRepository optionRepository;
    Question question;

    long randomNumber;


    public QuizEngine(
            QuestionRepository questionRepository,
            OptionRepository optionRepository,
            AnswerRepository answerRepository,
            ScoreRepository scoreRepository)
    {
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
        this.scoreRepository = scoreRepository;
        this.optionRepository = optionRepository;
    }

    public Question getQuestion() {
        generateQuestion();

        return question;
    }

    private void generateQuestion() {
        randomNumGenerator();

        if (questionRepository.existsById(randomNumber)) {
            question = questionRepository.findById(randomNumber).get();
        } else {
            generateQuestion();
        }
    }

    private void randomNumGenerator() {
        int maxQuestionId = (int) questionRepository.count();
        randomNumber = randomNum(maxQuestionId);
    }

    private int randomNum(int max) {
        SecureRandom random = new SecureRandom();
        return (random.nextInt(max - 1) + 1);
    }
}
