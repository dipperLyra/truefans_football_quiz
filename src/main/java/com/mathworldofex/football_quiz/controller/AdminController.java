package com.mathworldofex.football_quiz.controller;

import com.mathworldofex.football_quiz.JwtUtils;
import com.mathworldofex.football_quiz.enity.Answer;
import com.mathworldofex.football_quiz.enity.Question;
import com.mathworldofex.football_quiz.enity.QuestionOption;
import com.mathworldofex.football_quiz.payload.requests.QuestionOptionRequest;
import com.mathworldofex.football_quiz.repository.*;
import com.mathworldofex.football_quiz.services.admin.PopulateQuestionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class AdminController {

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    PopulateQuestionsService questions;
    @Autowired
    AnswerRepository answerRepository;
    @Autowired
    QuestionRepository questionRepository;
    @Autowired
    OptionRepository optionRepository;
    @Autowired
    RoleRepository roleRepository;

    @GetMapping("/admin/quiz/create")
    public String createQuestionForm(Model model) {
        model.addAttribute("questions", new QuestionOptionRequest());
        return "admin/questions/create-question";
    }

    @PostMapping("/admin/quiz/create")
    public String createQuestionAns(@Valid @ModelAttribute QuestionOptionRequest answerQuestion) {
        ModelAndView mav = new ModelAndView("fragments/message");
        // Set anwser
        Answer answer = new Answer();
        answer.setAnswer(answerQuestion.getAnswer());

        // Set options
        QuestionOption option = new QuestionOption(
                answerQuestion.getOptionA(),
                answerQuestion.getOptionB(),
                answerQuestion.getOptionC(),
                answerQuestion.getOptionD()
        );

        // Set question
        Question question = new Question(answerQuestion.getQuestion(), answer, option);
        try {
            questionRepository.save(question);
        } catch (DataAccessException e) {
            mav.addObject("message", "Error saving question: " + e);
            return "fragments/message";
        }

        mav.addObject("message", "Question created successfully");
        return "fragments/message";
    }
}
