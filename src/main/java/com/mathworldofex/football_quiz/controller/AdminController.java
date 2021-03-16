package com.mathworldofex.football_quiz.controller;

import com.mathworldofex.football_quiz.config.security.jwt.JwtUtils;
import com.mathworldofex.football_quiz.model.entity.*;
import com.mathworldofex.football_quiz.model.payload.records.QuestionClassificationRecord;
import com.mathworldofex.football_quiz.model.payload.requests.QuestionOptionRequest;
import com.mathworldofex.football_quiz.model.repository.*;
import com.mathworldofex.football_quiz.services.admin.PopulateQuestionsService;
import javassist.NotFoundException;
import org.hibernate.exception.DataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

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
    CategoryRepository categoryRepository;
    @Autowired
    SubCategoryRepository subCategoryRepository;
    @Autowired
    AnswerRepository answerRepository;
    @Autowired
    QuestionRepository questionRepository;
    @Autowired
    QuestionClassificationRepository classificationRepository;
    @Autowired
    OptionRepository optionRepository;
    @Autowired
    RoleRepository roleRepository;
    /*
        Endpoints render admin static pages
     */
    @GetMapping("/admin/quiz/create")
    public String createQuestionForm(Model model) {
        model.addAttribute("question", new QuestionOptionRequest());
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("subcategories", subCategoryRepository.findAll());
        return "admin/questions/create-question";
    }


    /*
        Endpoints to admin functions
     */
    @PostMapping("/admin/question/category")
    public String createCategory(@Valid @ModelAttribute QuestionClassificationRecord qClassificationRecord, Model model) {
        Category category = new Category(qClassificationRecord.category());
        categoryRepository.save(category);

        return createQuestionForm(model);
    }

    @PostMapping("/admin/question/subcategory")
    public String createSubCategory(@Valid @ModelAttribute QuestionClassificationRecord qClassificationRecord, Model model) {
        SubCategory subCategory = new SubCategory(qClassificationRecord.subCategory());
        subCategoryRepository.save(subCategory);
        return createQuestionForm(model);
    }

    @PostMapping("/admin/quiz/create")
    public String createQuestion(@Valid @ModelAttribute QuestionOptionRequest questionRequest, Model model) throws NotFoundException {
        Question question = setQuestions(questionRequest);
        try {
            questionRepository.save(question);
        } catch (DataException e) {
            return "error/error-message";
        }

        return createQuestionForm(model);
    }

    private Question setQuestions (QuestionOptionRequest questionRequest) throws NotFoundException {
        // Set answer
        Answer answer = new Answer(questionRequest.getAnswer());
        // Set options
        QuestionOption option = new QuestionOption(
                questionRequest.getOptionA(),
                questionRequest.getOptionB(),
                questionRequest.getOptionC(),
                questionRequest.getOptionD()
        );
        // Set category and sub-category
        Category category = categoryRepository.findByCategory(questionRequest.getCategory())
                .orElseThrow(() -> new NotFoundException("Could not find category: " + questionRequest.getCategory()));
        SubCategory subCategory = subCategoryRepository.findBySubCategory(questionRequest.getSubCategory())
                .orElseThrow(() -> new NotFoundException("Could not find subcategory: " + questionRequest.getSubCategory()));

        // Set question
        return new Question(
                questionRequest.getQuestion(),
                category,
                subCategory,
                answer,
                option
        );
    }
}
