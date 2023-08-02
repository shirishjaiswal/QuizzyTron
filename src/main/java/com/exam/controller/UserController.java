package com.exam.controller;

import com.exam.module.User;
import com.exam.service.JwtService;
import com.exam.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/signup")
    public ModelAndView makeUser (@ModelAttribute User user) {

        User saved = userService.createUser(user);

        ModelAndView modelAndView = new ModelAndView("/signUpResponse.html");

        String noteThree = "If you have any questions or need assistance, please don't hesitate to contact our support team.";
        String noteFour = "Thank you";

        modelAndView.addObject("noteThree", noteThree);
        modelAndView.addObject("noteFour", noteFour);

        if(saved == null) {
            String noteOne = "Registration Unsuccessfull Please try again!!";
            String noteTwo = "If already have an account Please log in using your email address and password to access all the features of our website.";
            modelAndView.addObject("textColor", "red");
            modelAndView.addObject("noteOne", noteOne);
            modelAndView.addObject("noteTwo", noteTwo);
        }
        else {
            String noteOne = "Congratulations! Your account has been successfully registered.";
            String noteTwo = "If already have an account Please log in using your email address and password to access all the features of our website.";

            modelAndView.addObject("imageURL", "/checked.png");
            modelAndView.addObject("noteOne", noteOne);
            modelAndView.addObject("noteTwo", noteTwo);
        }
        return modelAndView;
    }

    @PostMapping("/login")
    public ModelAndView login (@RequestParam ("userName") String userName,
                                @RequestParam ("password") String password) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, password));
        String token = null;
        ModelAndView modelAndView = new ModelAndView("/loginHome.html");
        if (authentication.isAuthenticated()) {
            token = jwtService.generateToken(userName);
            modelAndView.addObject("userName", userName);
            return modelAndView;
        } else {
            throw new UsernameNotFoundException("invalid user request !");
        }
    }

    @GetMapping("/{userName}")
    public User getUser(@PathVariable("userName") String userName) {
        return userService.findUserByUserName(userName);
    }

    @DeleteMapping("/{userId}")
    public String deleteUser(@PathVariable("userId") Long userId) {
        return userService.deleteUserById(userId);
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
