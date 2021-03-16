package com.mathworldofex.football_quiz.services.quiz;

import com.mathworldofex.football_quiz.model.entity.*;
import com.mathworldofex.football_quiz.model.payload.response.QuestionProjection;
import com.mathworldofex.football_quiz.model.repository.*;
import javassist.bytecode.stackmap.TypeData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Service
public class QuizService {
    @Autowired
    QuestionRepository questionRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    SubCategoryRepository subCategoryRepository;
    @Autowired
    QuestionClassificationRepository questionClassificationRepository;
    @Autowired
    AnswerRepository answerRepository;
    @Autowired
    ScoreRepository scoreRepository;
    @Autowired
    OptionRepository optionRepository;
    @Autowired
    UserRepository userRepository;

    Object[] questionIndices;
    Question question;

    private boolean foundAnswer;
    private static final Logger logger = Logger.getLogger( TypeData.ClassName.class.getName() );

    public List<QuestionProjection> questionsByClubLeague(String categoryRequest, String subCategoryRequest) {
        Category category = categoryRepository.findByCategory(categoryRequest).orElseThrow();
        SubCategory subCategory = subCategoryRepository.findBySubCategory(subCategoryRequest).orElseThrow();
        List<QuestionProjection> questions = questionRepository.findAllByCategoryIdAndSubCategoryId(category.getId(), subCategory.getId());
        return questions.isEmpty() ? new ArrayList<>() : questions;
    }

    public Question getQuestion() {
        int randomInt = randomQuestionId();
        questionRepository.findById((long) questionIndices[randomInt]).ifPresent(question1 -> {
            if (question1.getQuestion() != null) question = question1;
            else {
                try {
                    getQuestion();
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
        });
        return question;
    }

    public Boolean checkAnswer(Long questionId, String answer) {
        questionRepository.findById(questionId).ifPresent(question ->
                {
                    this.foundAnswer = answer.equalsIgnoreCase(question.getAnswer().getAnswer());
                    logger.info("questionId: " +questionId+ " question: " +question.getQuestion()+ " answer: " +answer);
                }
        );
        return foundAnswer;
    }

    public void saveScore(String username, int userScore) {
        User user = userRepository.findByUsername(username).isPresent() ? userRepository.findByUsername(username).get() : new User();
        Score score = new Score(userScore, user);
        scoreRepository.save(score);
    }

    private int randomQuestionId() {
        questionIndex();
        return new SecureRandom().nextInt(questionIndices.length);
    }

    private void questionIndex() {
        questionIndices = questionRepository.getAllId().toArray();
    }

}
