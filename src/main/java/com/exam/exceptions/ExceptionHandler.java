package com.exam.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    public String exceptionHandler(Exception ex, HttpServletRequest request) {

        String message = ex.getMessage();
        request.setAttribute("errorMessage", message);
        return "/redirect:/error";
    }
}
