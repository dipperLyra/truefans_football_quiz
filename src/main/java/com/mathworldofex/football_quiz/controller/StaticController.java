package com.mathworldofex.football_quiz.controller;

import com.mathworldofex.football_quiz.model.entity.User;
import com.mathworldofex.football_quiz.model.payload.requests.LoginRequest;
import com.mathworldofex.football_quiz.services.quiz.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
public class StaticController {

    @Autowired
    QuizService quizService;

    @GetMapping({"/", "/index", "/home", "/mwe", ""})
    public String home() {
        return "index";
    }

    @GetMapping({"/about-us", "/about", "/aboutus"})
    public String aboutUs() {
        return "misc/about-us";
    }

    @GetMapping({"/about-quiz", "/aboutquiz"})
    public String aboutQuiz() {
        return "quiz/pre-instruction";
    }

    @GetMapping({"/contact-us", "/contactus"})
    public String contactUs() {
        return "misc/contact-us";
    }

    @GetMapping({"/sign-up", "/signup"})
    public String signupForm(Model model) {
        model.addAttribute("user", new User());
        return "user/sign-up";
    }

    @GetMapping({"/sign-in", "/signin", "/login"})
    public String signin(Model model) {
        model.addAttribute("user", new LoginRequest());
        return "user/sign-in";
    }

}
