package com.mathworldofex.football_quiz.controller;

import com.mathworldofex.football_quiz.model.entity.Question;
import com.mathworldofex.football_quiz.model.payload.requests.LoginRequest;
import com.mathworldofex.football_quiz.model.payload.requests.QuizSetUpRequest;
import com.mathworldofex.football_quiz.model.payload.requests.SubmitAnswerRequest;
import com.mathworldofex.football_quiz.model.repository.CategoryRepository;
import com.mathworldofex.football_quiz.model.repository.SubCategoryRepository;
import com.mathworldofex.football_quiz.services.payment.PaymentIntegrationService;
import com.mathworldofex.football_quiz.services.quiz.QuizService;
import com.mathworldofex.football_quiz.services.user.UserService;
import javassist.bytecode.stackmap.TypeData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.URISyntaxException;
import java.security.Principal;
import java.util.*;
import java.util.logging.Logger;

@Controller
@SessionAttributes()
public class QuizController {
    @Autowired
    QuizService quizService;
    @Autowired
    PaymentIntegrationService paymentService;
    @Autowired
    UserService userService;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    SubCategoryRepository subCategoryRepository;

    private Integer counter = 0;
    long startTime = System.currentTimeMillis();
    long elapsedTime = 0L;
    private Integer score = 0;
    private boolean gameover = false;
    private Map<String, String> map = new HashMap<>();
    private Principal principal;


    private static final Logger logger = Logger.getLogger( TypeData.ClassName.class.getName() );

    @GetMapping("/quiz")
    public String quizForm(Model model, HttpSession session) {
        @SuppressWarnings("unchecked")
        List<String> quizList = (List<String>) session.getAttribute("QUIZ_SESSION");
        model.addAttribute("quizSession", quizList!=null ? quizList : new ArrayList<>());

        Question question = quizService.getQuestion();
        model.addAttribute("question", question);
        model.addAttribute("option", question.getQuestionOption());
         return "quiz/question";
    }

    @GetMapping("/quiz/answer")
    public @ResponseBody Map<String, Object> getQuestion(HttpServletRequest request, Principal principal) throws Throwable {
        Question question = quizService.getQuestion();
        elapsedTime = System.currentTimeMillis() - startTime;
        logger.info("Elapsed time: "  + elapsedTime);
        if ( counter >= 10 || elapsedTime > (30 * 1000) ) {
            logger.info("##### Save this score: " + this.score + " For Principal: " + principal.getName());
            quizService.saveScore(principal.getName(), this.score);
            request.getSession().invalidate();
            this.gameover = true;
            return Map.of("gameover", gameover);
        }
        return Map.of("question", question, "option", question.getQuestionOption(), "gameover", this.gameover);
    }

    @PostMapping("/quiz/answer") public @ResponseBody
    Map<String, String> receiveAnswer(@RequestBody SubmitAnswerRequest requestBody, HttpServletRequest request) {
        List<String> quizList = (List<String>) request.getSession().getAttribute("QUIZ_SESSION");
        if (quizList == null) {
            quizList = new ArrayList<>();
            request.getSession().setAttribute("QUIZ_SESSION", quizList);
        }
        quizList.add(requestBody.getQuestionId());
        request.getSession().setAttribute("QUIZ_SESSION", quizList);
        logger.info("########## Quiz list: " + quizList);

        if (elapsedTime <= (30 * 1000) && counter <= 10) {
            this.counter++;
            boolean correctAnswer = quizService.checkAnswer(Long.valueOf(requestBody.getQuestionId()), requestBody.getAnswer());
            if (correctAnswer) {
                    this.score++;
                    logger.info("Correct answer. Score: " + score);
            }
        }
        return Map.of("result", "Inline changes with ajax");
    }

    @GetMapping("/payment")
    public String paymentForm(Principal principal, Model model) {
        if (principal == null) {
            model.addAttribute("user", new LoginRequest());
            return "user/sign-in";
        }
        return "payment/paystack-pop-up.html";
    }

    @GetMapping("/payment/verify")
    public String verifyPayment(@RequestParam String reference, Principal principal) throws URISyntaxException {
        if (paymentService.verifyPayment(reference, principal.getName())) {
            userService.updateUserRole(principal.getName());
            return "quiz/post-instruction";
        } else return "quiz/payment-error";
    }

    @GetMapping("/quiz/setup")
//    @Cacheable(value = "questions")
    public String quizSetup(Model model) {
        model.addAttribute("quizSetup", new QuizSetUpRequest());
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("subCategories", subCategoryRepository.findAll());
        quizService.questionsByClubLeague("EPL", "Liverpool");
        return "quiz/post-instruction";
    }


/*    @PostMapping("/quiz/setup") @ResponseBody
    private String quizSetup(@Valid @ModelAttribute QuizSetUpRequest setUp, Model model) {
        List<Question> questions = quizService.questionsByClubLeague(setUp.getCategory(), setUp.getSubCategory());
        Collections.shuffle(questions);
        logger.info("Questions: " + Arrays.toString(questions.toArray()));
        Question question = null;
        model.addAttribute("question", question);
        model.addAttribute("option", question);
        return "quiz/question";
    }*/

/*    @GetMapping("/quiz/test") public @ResponseBody
    List<Question> quizTester() {
        List<Question> questions = quizService.questionsByClubLeague("EPL", "Chelsea");
        Collections.shuffle(questions);
        logger.info("Question: " + questions);
        return questions;
    }*/


    @GetMapping({"/quiz/gameover", "/quiz/game-over"})
    public String gameOver() {
        return "quiz/game-over";
    }
}
