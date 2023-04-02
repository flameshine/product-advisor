package com.flameshine.assistant.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import lombok.extern.slf4j.Slf4j;

import com.flameshine.assistant.util.Constants;

@ControllerAdvice
@Slf4j
public class ErrorController {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ModelAndView handle(Exception e) {

        log.error("An unhandled exception has occurred", e);

        return new ModelAndView(Constants.ERROR_PATH)
            .addObject("message", e.getMessage());
    }
}