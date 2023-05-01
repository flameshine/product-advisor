package com.flameshine.advisor.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import com.flameshine.advisor.util.Constants;

@ControllerAdvice
public class ErrorController {

    @ExceptionHandler(Exception.class)
    protected ModelAndView handle(Exception e) {
        return new ModelAndView(Constants.ERROR_PATH)
            .addObject("message", e.getMessage());
    }
}