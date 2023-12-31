package com.exam.controller;

import com.exam.dto.LoginRequestDetails;
import com.exam.dto.UserNameToken;
import com.exam.module.UserEntity;
import com.exam.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/authenticate")
    public String login(@ModelAttribute LoginRequestDetails loginRequestDetails, HttpServletRequest request) {

        String token = userService.verify(loginRequestDetails);

        ModelAndView modelAndView = new ModelAndView("/String.html");

        request.setAttribute("userName", loginRequestDetails.getUserName());
        request.setAttribute("token", token);
        return "home";
    }

    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }

    @PostMapping("/signup")
    public String makeUser(@ModelAttribute UserEntity userEntity, HttpServletRequest request) {

        UserEntity saved = userService.createUser(userEntity);

        if (saved == null) {
            String noteOne = "Registration Unsuccessful Please try again!!";
            request.setAttribute("textColor", "red");
            request.setAttribute("noteOne", noteOne);
        } else {
            String noteOne = "Congratulations! Your account has been successfully registered.";
            request.setAttribute("imageURL", "/checked.png");
            request.setAttribute("noteOne", noteOne);
        }
        return "signUpStatus";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }

    private long refreshCount = 0;

    @GetMapping("/refresh-page")
    public String refreshPage(Model model) {
        if(refreshCount == Long.MAX_VALUE) refreshCount = 0;
        refreshCount++; // Increment the refresh count
        model.addAttribute("refreshCount", refreshCount);
        return "autoRefresh"; // Returns the Thymeleaf template name
    }
}

