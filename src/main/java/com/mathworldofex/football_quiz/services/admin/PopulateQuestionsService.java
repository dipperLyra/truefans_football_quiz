package com.mathworldofex.football_quiz.services.admin;

import com.mathworldofex.football_quiz.model.entity.Answer;
import com.mathworldofex.football_quiz.model.entity.Question;
import com.mathworldofex.football_quiz.model.entity.QuestionOption;
import com.mathworldofex.football_quiz.model.repository.AnswerRepository;
import com.mathworldofex.football_quiz.model.repository.OptionRepository;
import com.mathworldofex.football_quiz.model.repository.QuestionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PopulateQuestionsService {

    QuestionRepository questionRepo;
    OptionRepository optionRepo;
    AnswerRepository answerRepo;

    public PopulateQuestionsService(QuestionRepository question, OptionRepository option, AnswerRepository answer) {
        this.questionRepo = question;
        this.optionRepo = option;
        this.answerRepo = answer;
    }

    public Question createQuestion(Question question) {
        try {
            return questionRepo.save(question);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Question> listQuestions() {
        return questionRepo.findAll();
    }

    public String createOptions(QuestionOption option) {
        try {
            optionRepo.save(option);
            return "Option created";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public String createAnswers(Answer answer) {
        try {
            answerRepo.save(answer);
            return "Answer created";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
