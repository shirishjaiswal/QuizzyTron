package com.exam.controller;

import com.exam.dto.UserNameToken;
import com.exam.module.Questions;
import com.exam.module.Quiz;
import com.exam.service.QuestionService;
import com.exam.service.QuizService;
import com.exam.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
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

    @GetMapping("/")
    public String home() {
        return "redirect:/";
    }

    @PostMapping("/")
    public String home(@RequestParam(value = "userName") String userName,
                       @RequestParam(value = "token") String token,
                       HttpServletRequest request){
        userService.userRequestValidate(token, userName);
        request.setAttribute("token", token);
        request.setAttribute("userName", userName);
        return "home";
    }

    @PostMapping("/quiz")
    public String getQuizzes(@ModelAttribute UserNameToken userNameToken, HttpServletRequest request) {
        userService.userRequestValidate(userNameToken.getToken(), userNameToken.getUserName());
        List<String> quizzes = questionService.getQuizList();
        request.setAttribute("userName", userNameToken.getUserName());
        request.setAttribute("token", userNameToken.getToken());
        request.setAttribute("imageURL", "/choice.png");
        request.setAttribute("pageTitle", "Select Quiz");
        request.setAttribute("quizzes", quizzes);
        request.setAttribute("operation", "Click on Quiz to Begin");
        return "quizzes";
    }

    @PostMapping("/getQuestions/{quizName}")
    public String getQuiz (@PathVariable("quizName") String quizName,
                           @ModelAttribute UserNameToken userNameToken,
                           HttpServletRequest request){
        userService.userRequestValidate(userNameToken.getToken(), userNameToken.getUserName());
        List<Questions> questionsList = questionService.getQuiz(quizName);
        request.setAttribute("userName", userNameToken.getUserName());
        request.setAttribute("token", userNameToken.getToken());
        request.setAttribute("quizName", questionsList.get(0).getQuizName());
        request.setAttribute("questionsList", questionsList);
        return "solveQuiz";
    }

    @PostMapping("/verify")
    public String verifyAnswers(@RequestParam HashMap<String, String> answer, HttpServletRequest request) {
        userService.userRequestValidate(answer.get("token"), answer.get("userName"));
        int marks = questionService.verifyAnswer(answer);
        int noOfQuestion = questionService.noOfQuestion(answer.get("quizName"));
        quizService.userAttendedQuiz(userService.findUserByUserName(answer.get("userName")), answer.get("quizName"), marks, noOfQuestion);
        String greeting = "";
        String note = "";
        if(noOfQuestion/2 >= marks) {
            request.setAttribute("imageURL", "/failed.png");
            request.setAttribute("textColor", "red");
            greeting = "Not Up to the Mark";
            note = "Prepare More";
        }
        else {
            request.setAttribute("imageURL", "/pass.png");
            greeting = "Congratulations!!!";
            note = "Keep it Up!!";
        }
        String vMark = marks + "/" + noOfQuestion;
        request.setAttribute("userName", answer.get("userName"));
        request.setAttribute("token", answer.get("token"));
        request.setAttribute("greetings", greeting);
        request.setAttribute("marks", vMark);
        request.setAttribute("note", note);
        return "marks";
    }

    @PostMapping("/profile")
    public String profile (@ModelAttribute UserNameToken userNameToken, HttpServletRequest request) {
        userService.userRequestValidate(userNameToken.getToken(), userNameToken.getUserName());
        if(userService.adminOrUser(userNameToken.getUserName()).equals("ADMIN")) {
            request.setAttribute("role", "ADMIN");
        }
        List<Quiz> quizList = quizService.findByUserName(userNameToken.getUserName());
        request.setAttribute("quizList", quizList);
        request.setAttribute("token", userNameToken.getToken());
        request.setAttribute("userName", userNameToken.getUserName());

        return "userData";
    }

    @PostMapping("/logout")
    public RedirectView logout(@ModelAttribute UserNameToken userNameToken) {
        userService.userRequestValidate(userNameToken.getToken(), userNameToken.getUserName());
        int logout = userService.logout(userNameToken.getToken(), userNameToken.getUserName());
        if(logout == 0) {
            try {
                throw new Exception("Not Signed In");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        RedirectView redirectView = new RedirectView("/");
        return redirectView;
    }

    @GetMapping("/about")
    public String about() {
        return "redirect:/about";
    }

    @DeleteMapping("/{userId}")
    public String deleteUser(@PathVariable("userId") Long userId) {
        return userService.deleteUserById(userId);
    }
}
