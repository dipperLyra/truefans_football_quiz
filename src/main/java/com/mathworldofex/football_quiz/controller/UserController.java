package com.mathworldofex.football_quiz.controller;

import com.mathworldofex.football_quiz.enity.User;
import com.mathworldofex.football_quiz.services.users.UserSignUpService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@Controller
public class UserController {

    UserSignUpService signUpService;

    public UserController(UserSignUpService signUpService) {
        this.signUpService = signUpService;
    }

    @PostMapping("/signup")
    public String signup(@Valid @RequestBody User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "user/sign-up";
        }

        signUpService.create(user);
        model.addAttribute("user", new User());
        return "redirect:/index";
    }

}
