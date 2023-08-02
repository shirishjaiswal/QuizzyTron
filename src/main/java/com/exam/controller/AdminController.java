package com.exam.controller;

import com.exam.module.Questions;
import com.exam.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private QuestionService questionService;

    @PostMapping("/addquiz")
    public String addQuiz (@RequestBody ArrayList<Questions> questions) {
        return questionService.addQuiz(questions);
    }

    @GetMapping("/getQuestions/{quizName}")
    public ModelAndView getQuiz (@PathVariable("quizName") String quizName){

        List<Questions> questionsList = questionService.getQuiz(quizName);

        ModelAndView modelAndView = new ModelAndView("/quizPage.html");

        modelAndView.addObject("quizName", questionsList.get(0).getQuizName());
        modelAndView.addObject("questionsList", questionsList);
        return modelAndView;
    }

    @GetMapping("/getQuizs")
    public List<String> getQuizs() {
        return questionService.getQuizList();
    }
    @PostMapping("/verify")
    public ModelAndView verifyAnswers(@RequestParam HashMap<String, String> answer) {
        System.out.println(answer);
        int marks = questionService.verifyAnswer(answer);
        int noOfQuestion = questionService.noOfQuestion(answer.get("quizName"));

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
        modelAndView.addObject("greetings", greeting);
        modelAndView.addObject("marks", vMark);
        modelAndView.addObject("note", note);
        return modelAndView;
    }

    @GetMapping("/quizz")
    public ModelAndView getQuizzes() {
        List<String> quizzes = questionService.getQuizList();

        ModelAndView modelAndView = new ModelAndView("/selectQuiz.html");

        modelAndView.addObject("quizzes", quizzes);

        return modelAndView;
    }

    @ExceptionHandler
    public ModelAndView exceptionHandler (Exception ex) {
        ModelAndView modelAndView = new ModelAndView("error");

        String message = ex.getMessage();
        modelAndView.addObject("imageURL", "/cancel.png");
        modelAndView.addObject("errorMessage", message);
        return modelAndView;
    }

}
