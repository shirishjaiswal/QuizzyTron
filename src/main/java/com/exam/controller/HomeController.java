package com.exam.controller;

import com.exam.dto.LoginRequestDetails;
import com.exam.dto.UserNameToken;
import com.exam.module.UserEntity;
import com.exam.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

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
        return "main";
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

    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }

    @PostMapping("/signup")
    public String makeUser(@ModelAttribute UserEntity userEntity, HttpServletRequest request) {

        UserEntity saved = userService.createUser(userEntity);

        String noteThree = "If you have any questions or need assistance, please don't hesitate to contact our support team.";
        String noteFour = "Thank you";

        request.setAttribute("noteThree", noteThree);
        request.setAttribute("noteFour", noteFour);

        if (saved == null) {
            String noteOne = "Registration Unsuccessfull Please try again!!";
            String noteTwo = "If already have an account Please log in using your email address and password to access all the features of our website.";
            request.setAttribute("textColor", "red");
            request.setAttribute("noteOne", noteOne);
            request.setAttribute("noteTwo", noteTwo);
        } else {
            String noteOne = "Congratulations! Your account has been successfully registered.";
            String noteTwo = "If already have an account Please log in using your email address and password to access all the features of our website.";

            request.setAttribute("imageURL", "/checked.png");
            request.setAttribute("noteOne", noteOne);
            request.setAttribute("noteTwo", noteTwo);
        }
        return "signUpStatus";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }
}

