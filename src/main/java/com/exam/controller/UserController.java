package com.exam.controller;

import com.exam.dto.UserNameToken;
import com.exam.module.Questions;
import com.exam.module.Quiz;
import com.exam.module.User;
import com.exam.service.QuestionService;
import com.exam.service.QuizService;
import com.exam.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private QuizService quizService;

    @PostMapping("/")
    public String home (@RequestParam("token")  String token,
                        @RequestParam("userName") String userName, HttpServletRequest request) {
        userService.isValidRequest(token, userName);

        request.setAttribute("token", token);
        request.setAttribute("userName", userName);
        return "main";
    }

    @PostMapping("/quizz")
    public String getQuizzes(@ModelAttribute UserNameToken userNameToken, HttpServletRequest request) {
        userService.isValidRequest(userNameToken.getToken(), userNameToken.getUserName());
        List<String> quizzes = questionService.getQuizList();
        ModelAndView modelAndView = new ModelAndView("/selectQuiz.html");
        modelAndView.addObject("userName", userNameToken.getUserName());
        modelAndView.addObject("token", userNameToken.getToken());
        modelAndView.addObject("quizzes", quizzes);
        request.setAttribute("userName", userNameToken.getUserName());
        request.setAttribute("token", userNameToken.getToken());
        request.setAttribute("quizzes", quizzes);
        return "selectQuiz";
    }

    @PostMapping("/getQuestions/{quizName}")
    public ModelAndView getQuiz (@PathVariable("quizName") String quizName,
                                 @RequestParam("token") String token,
                                 @RequestParam("userName") String userName){
        userService.isValidRequest(token, userName);
        List<Questions> questionsList = questionService.getQuiz(quizName);

        ModelAndView modelAndView = new ModelAndView("/quizPage.html");
        modelAndView.addObject("userName", userName);
        modelAndView.addObject("token", token);
        modelAndView.addObject("quizName", questionsList.get(0).getQuizName());
        modelAndView.addObject("questionsList", questionsList);
        return modelAndView;
    }

    @PostMapping("/verify")
    public ModelAndView verifyAnswers(@RequestParam HashMap<String, String> answer) {
        userService.isValidRequest(answer.get("token"), answer.get("userName"));
        int marks = questionService.verifyAnswer(answer);
        int noOfQuestion = questionService.noOfQuestion(answer.get("quizName"));
        quizService.userAttendedQuiz(userService.findUserByUserName(answer.get("userName")), answer.get("quizName"), marks);
        ModelAndView modelAndView = new ModelAndView("/marks.html");
        String greeting = "";
        String note = "";
        if(noOfQuestion/2 >= marks) {
            modelAndView.addObject("imageURL", "/failed.png");
            modelAndView.addObject("textColor", "red");
            greeting = "Not Up to the Mark";
            note = "Prepare More";
        }
        else {
            modelAndView.addObject("imageURL", "/pass.png");
            greeting = "Congratulations!!!";
            note = "Keep it Up!!";
        }
        String vMark = marks + "/" + noOfQuestion;
        modelAndView.addObject("userName", answer.get("userName"));
        modelAndView.addObject("token", answer.get("token"));
        modelAndView.addObject("greetings", greeting);
        modelAndView.addObject("marks", vMark);
        modelAndView.addObject("note", note);
        return modelAndView;
    }

    @GetMapping("/profile/{userName}")
    public String profile (@PathVariable String userName, HttpServletRequest request) {
        String token = userService.isValidRequest(userName);
        List<Quiz> quizList = quizService.findByUserName(userName);
        System.out.println(quizList);
        request.setAttribute("token", token);
        request.setAttribute("userName", userName);
        request.setAttribute("quizList", quizList);

        return "userData";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }

    @GetMapping("/{userName}")
    public User getUser(@PathVariable("userName") String userName) {
        return userService.findUserByUserName(userName);
    }

    @DeleteMapping("/{userId}")
    public String deleteUser(@PathVariable("userId") Long userId) {
        return userService.deleteUserById(userId);
    }
}
