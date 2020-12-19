package com.mathworldofex.football_quiz.controller;

import com.mathworldofex.football_quiz.enity.Answer;
import com.mathworldofex.football_quiz.enity.Question;
import com.mathworldofex.football_quiz.enity.QuestionOption;
import com.mathworldofex.football_quiz.repository.AnswerRepository;
import com.mathworldofex.football_quiz.repository.OptionRepository;
import com.mathworldofex.football_quiz.repository.QuestionRepository;
import com.mathworldofex.football_quiz.payload.requests.AnswerRequest;
import com.mathworldofex.football_quiz.services.admin.PopulateQuestions;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class AdminController {

    PopulateQuestions questions;
    AnswerRepository answerRepository;
    QuestionRepository questionRepository;
    OptionRepository optionRepository;

    public AdminController(
            PopulateQuestions questions,
            AnswerRepository answerRepository,
            QuestionRepository questionRepository,
            OptionRepository optionRepository)
    {
        this.questions = questions;
        this.answerRepository = answerRepository;
        this.questionRepository = questionRepository;
        this.optionRepository = optionRepository;
    }

    @PostMapping("/answer")
    public List<Object> createQuestionAns(@RequestBody List<AnswerRequest> answerQuestions) {
        List<Object> response = new ArrayList<>();

        for (AnswerRequest answerQuestion : answerQuestions) {
            // Set answer
            Answer answer = new Answer();
            answer.setAnswer(answerQuestion.getAnswer());

            // Set options
            QuestionOption option = new QuestionOption(
                    answerQuestion.getOptionA(),
                    answerQuestion.getOptionB(),
                    answerQuestion.getOptionC(),
                    answerQuestion.getOptionD());

            // Set question
            Question question = new Question();
            question.setQuestion(answerQuestion.getQuestion());
            question.setAnswer(answer);
            question.setQuestionOption(option);


            response.add(questionRepository.save(question));
            response.add(answerRepository.save(answer));
            response.add(optionRepository.save(option));
        }
        return response;
    }
}
