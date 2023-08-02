package com.exam.controller;

import com.exam.dto.LoginRequestDetails;
import com.exam.module.User;
import com.exam.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public ModelAndView login(@ModelAttribute LoginRequestDetails loginRequestDetails) {

        String token = userService.verify(loginRequestDetails);

        ModelAndView modelAndView = new ModelAndView("/main.html");

        modelAndView.addObject("userName", loginRequestDetails.getUserName());
        modelAndView.addObject("token", token);
        return modelAndView;
    }

    @PostMapping("/logout")
    public String logout(@RequestParam("token")  String token,
                         @RequestParam("userName") String userName) {
        userService.isValidRequest(token, userName);
        int logout = userService.logout(token, userName);
        if(logout == 0) {
            try {
                throw new Exception("Not Signed In");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return "index";
    }

    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }

    @PostMapping("/signup")
    public ModelAndView makeUser(@ModelAttribute User user) {

        User saved = userService.createUser(user);

        ModelAndView modelAndView = new ModelAndView("/signUpStatus.html");

        String noteThree = "If you have any questions or need assistance, please don't hesitate to contact our support team.";
        String noteFour = "Thank you";

        modelAndView.addObject("noteThree", noteThree);
        modelAndView.addObject("noteFour", noteFour);

        if (saved == null) {
            String noteOne = "Registration Unsuccessfull Please try again!!";
            String noteTwo = "If already have an account Please log in using your email address and password to access all the features of our website.";
            modelAndView.addObject("textColor", "red");
            modelAndView.addObject("noteOne", noteOne);
            modelAndView.addObject("noteTwo", noteTwo);
        } else {
            String noteOne = "Congratulations! Your account has been successfully registered.";
            String noteTwo = "If already have an account Please log in using your email address and password to access all the features of our website.";

            modelAndView.addObject("imageURL", "/checked.png");
            modelAndView.addObject("noteOne", noteOne);
            modelAndView.addObject("noteTwo", noteTwo);
        }
        return modelAndView;
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }
}

