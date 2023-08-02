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

    @GetMapping("/getQuizs")
    public List<String> getQuizs() {
        return questionService.getQuizList();
    }


}
