package com.mathworldofex.football_quiz.controller;

import com.mathworldofex.football_quiz.enity.Question;
import com.mathworldofex.football_quiz.services.quiz.QuizEngine;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class QuizController {

    QuizEngine quizEngine;

    public QuizController(QuizEngine quizEngine) {
        this.quizEngine = quizEngine;
    }

    @GetMapping("/quiz/instruction")
    public String quizPage() {

        return "quiz/instruction";
    }

    @GetMapping("/quiz")
    public String quizForm(Model model) {
        Question question = quizEngine.getQuestion();
        model.addAttribute("question", question.getQuestion());
        model.addAttribute("option", question.getQuestionOption());

         return "quiz/index";
    }

    @PostMapping("/quiz")
    public String submitAnswer(@ModelAttribute Question question, Model model) {
        model.addAttribute("question", question);
        return "quiz/index";
    }
}
