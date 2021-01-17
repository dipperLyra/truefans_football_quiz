package com.mathworldofex.football_quiz.services.quiz;

import com.mathworldofex.football_quiz.enity.Question;
import com.mathworldofex.football_quiz.repository.AnswerRepository;
import com.mathworldofex.football_quiz.repository.OptionRepository;
import com.mathworldofex.football_quiz.repository.QuestionRepository;
import com.mathworldofex.football_quiz.repository.ScoreRepository;
import javassist.bytecode.stackmap.TypeData;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Supplier;
import java.util.logging.Logger;

@Service
public class QuizService {

    QuestionRepository questionRepository;
    AnswerRepository answerRepository;
    ScoreRepository scoreRepository;
    OptionRepository optionRepository;
    Question question;

    private boolean foundAnswer;
    private static final Logger LOGGER = Logger.getLogger( TypeData.ClassName.class.getName() );


    public QuizService(
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

    public Question getQuestion() throws Throwable {

        return questionEngine();
    }

    public Question questionEngine() throws Throwable {
        List<Long> listId = questionRepository.getAllId();
        Object[] objects = listId.toArray();

        int random = new SecureRandom().nextInt(objects.length);
        return questionRepository.findById((long) objects[random])
                .orElseThrow((Supplier<Throwable>) NoSuchElementException::new);
    }

    public Boolean checkAnswer(Long questionId, String answer) {
        questionRepository.findById(questionId).ifPresent(question ->
                {
                    this.foundAnswer = answer.equalsIgnoreCase(question.getAnswer().getAnswer());
                    LOGGER.info("questionId: " +questionId+ " question: " +question.getQuestion()+ " answer: " +answer);
                }
        );

        return this.foundAnswer;
    }
}
