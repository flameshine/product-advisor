package com.flameshine.advisor.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.validation.Validator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import com.flameshine.advisor.entity.User;
import com.flameshine.advisor.service.Saver;
import com.flameshine.advisor.util.WebPaths;

/**
 * Controller for the "/registration" path, which allows users to register a new account.
 */

@Controller
@RequestMapping(WebPaths.REGISTRATION)
@RequiredArgsConstructor
public class RegistrationController {

    private final Validator validator;
    private final Saver<User> saver;

    @GetMapping
    public ModelAndView registration() {
        return new ModelAndView(WebPaths.REGISTRATION)
            .addObject("user", new User());
    }

    @PostMapping
    public ModelAndView registration(@Valid User user, BindingResult result) {

        validator.validate(user, result);

        if (result.hasErrors()) {
            return new ModelAndView(WebPaths.REGISTRATION);
        }

        saver.save(user);

        return new ModelAndView(WebPaths.LOGIN)
            .addObject("message", "User has been registered successfully");
    }
}