package com.mathworldofex.football_quiz.controller;

import com.mathworldofex.football_quiz.enity.Question;
import com.mathworldofex.football_quiz.payload.requests.SubmitAnswerRequest;
import com.mathworldofex.football_quiz.services.quiz.QuizService;
import javassist.bytecode.stackmap.TypeData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.logging.Logger;

@Controller
@SessionAttributes()
public class QuizController {
    QuizService quizService;
    private Integer counter = 0;
    private Integer score = 0;
    private boolean gameover = false;
    private Map<String, String> map = new HashMap<>();
    private static final Logger LOGGER = Logger.getLogger( TypeData.ClassName.class.getName() );

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @GetMapping("/quiz")
    public String quizForm(Model model, HttpSession session) throws Throwable {
        @SuppressWarnings("unchecked")
        List<String> quizList = (List<String>) session.getAttribute("QUIZ_SESSION");
        model.addAttribute("quizSession", quizList!=null ? quizList : new ArrayList<>());

        Question question = quizService.getQuestion();
        model.addAttribute("question", question);
        model.addAttribute("option", question.getQuestionOption());
         return "quiz/question";
    }

    @PostMapping("/quiz")
    public String submitAnswer(@ModelAttribute Question question, Model model, HttpServletRequest request) throws Throwable {
        List<String> quizList = (List<String>) request.getSession().getAttribute("QUIZ_SESSION");
        if (quizList == null) {
            quizList = new ArrayList<>();
            // if notes object is not present in session, set notes in the request session
            request.getSession().setAttribute("QUIZ_SESSION", quizList);
        }
        quizList.add(question.getId().toString());
        request.getSession().setAttribute("QUIZ_SESSION", quizList);
        boolean correctAnswer = quizService.checkAnswer(question.getId(), question.getAnswer().getAnswer());
        this.counter++;
        if (correctAnswer) {
            this.score++;
        }
        LOGGER.info("counter: " +counter+ " score: " +score);
        Question question1 = quizService.getQuestion();
        model.addAttribute("question", question1.getQuestion());
        model.addAttribute("option", question1.getQuestionOption());
        return "quiz/question";
    }

    @GetMapping("/quiz-ans")
    public @ResponseBody Map<String, Object> retrieveQuestion() throws Throwable {
        Question question = quizService.getQuestion();
        if (counter > 10) {
            this.gameover = true;
            return Map.of("gameover", gameover);
        }

        return Map.of("question", question, "option", question.getQuestionOption(), "gameover", this.gameover);
    }

    @PostMapping("/quiz-ans") public @ResponseBody
    Map<String, String> receiveAnswer(@RequestBody SubmitAnswerRequest request, Model model, HttpServletRequest request1) {
        long startTime = System.currentTimeMillis();
        long elapsedTime = 0L;

        List<String> quizList = (List<String>) request1.getSession().getAttribute("QUIZ_SESSION");
        if (quizList == null) {
            quizList = new ArrayList<>();
            request1.getSession().setAttribute("QUIZ_SESSION", quizList);
        }

        quizList.add(request.getQuestionId());
        request1.getSession().setAttribute("QUIZ_SESSION", quizList);

        if (elapsedTime < 3*1000) {
            if (this.counter <= 10) {
                boolean correctAnswer = quizService.checkAnswer(Long.valueOf(request.getQuestionId()), request.getAnswer());

                this.counter++;

                if (correctAnswer) {
                    this.score++;
                    LOGGER.info("Correct answer. Score: " + score);
                } else LOGGER.info("Wrong answer. Score: " + score);

                LOGGER.info("counter: " +counter+ " score: " +score);
            } else gameOver() ;

            elapsedTime = (new Date()).getTime() - startTime;
        }

        return Map.of("result", "Inline changes with ajax");
    }

    @GetMapping({"/quiz/gameover", "/quiz/game-over"})
    public String gameOver() {
        return "quiz/game-over";
    }


}
