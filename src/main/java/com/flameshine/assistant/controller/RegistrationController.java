package com.flameshine.assistant.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.validation.Validator;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.servlet.ModelAndView;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import com.flameshine.assistant.entity.User;
import com.flameshine.assistant.service.Saver;
import com.flameshine.assistant.util.Constants;

@Controller
@RequestMapping(Constants.REGISTRATION_PATH)
@RequiredArgsConstructor
public class RegistrationController {

    private final Saver<User> saver;
    private final Validator validator;

    @InitBinder
    private void bindValidator(WebDataBinder binder) {
        binder.setValidator(validator);
    }

    @GetMapping
    public ModelAndView registration() {
        return new ModelAndView(Constants.REGISTRATION_PATH)
            .addObject("user", new User());
    }

    @PostMapping
    public ModelAndView registration(@Valid User user, BindingResult result) {

        if (result.hasErrors()) {
            return new ModelAndView(Constants.REGISTRATION_PATH);
        }

        saver.save(user);

        return new ModelAndView(Constants.LOGIN_PATH)
            .addObject("message", "User has been registered successfully");
    }
}