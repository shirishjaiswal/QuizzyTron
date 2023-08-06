package com.exam.controller;

import com.exam.dto.UserNameToken;
import com.exam.module.Questions;
import com.exam.module.UserEntity;
import com.exam.service.QuestionService;
import com.exam.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private UserService userService;

    @PostMapping("/add-quiz")
    public String addQuiz (@ModelAttribute UserNameToken userNameToken,
                           HttpServletRequest request) {

        userService.adminRequestValidate(userNameToken.getToken(), userNameToken.getUserName());
        request.setAttribute("userName", userNameToken.getUserName());
        request.setAttribute("token", userNameToken.getToken());
        return "addQuestion";
    }

    @PostMapping("/add-quizQuestions")
    public String addQuiz (@RequestParam Map<String, String> map, HttpServletRequest request) {

        String userName = map.get("userName");
        String token = map.get("token");
        userService.adminRequestValidate(token, userName);
        String message = "!! Uploading Quiz Failed !!\nPlease Try Again";
        if(questionService.addQuiz(map)) {
            message = map.get("quizName")+" \nSuccessfully Saved";
        }

        request.setAttribute("saved", true);
        request.setAttribute("userName", userName);
        request.setAttribute("token", token);
        request.setAttribute("message", message);
        return "queSaved";
    }

    @GetMapping("/getQuizs")
    public List<String> getQuizs() {
        return questionService.getQuizList();
    }

    @GetMapping("/{userName}")
    public UserEntity getUser(@PathVariable("userName") String userName) {
        return userService.findUserByUserName(userName);
    }

    @GetMapping("/delQuiz")
    public String quizList(@ModelAttribute UserNameToken userNameToken, HttpServletRequest request) {
        userService.userRequestValidate(userNameToken.getToken(), userNameToken.getUserName());
        List<String> quizzes = questionService.getQuizList();
        request.setAttribute("userName", userNameToken.getUserName());
        request.setAttribute("token", userNameToken.getToken());
        request.setAttribute("quizzes", quizzes);
        return "delQuiz";
    }

    @PostMapping("/delQuiz/{quizName}")
    public String getQuiz (@PathVariable("quizName") String quizName,
                           @ModelAttribute UserNameToken userNameToken,
                           RedirectAttributes redirectAttributes){

        userService.userRequestValidate(userNameToken.getToken(), userNameToken.getUserName());
        questionService.deleteQuiz(quizName);
        List<Questions> questionsList = questionService.getQuiz(quizName);
        redirectAttributes.addAttribute("token", userNameToken.getToken());
        redirectAttributes.addAttribute("userName", userNameToken.getUserName());
        return "redirect:/admin/delQuiz";
    }
}
