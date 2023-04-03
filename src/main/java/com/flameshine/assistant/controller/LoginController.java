package com.flameshine.assistant.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.flameshine.assistant.util.Constants;

@Controller
public class LoginController {

    @GetMapping(Constants.LOGIN_PATH)
    public String login(Principal principal) {
        return principal != null ? Constants.RECOGNITION_PATH : Constants.LOGIN_PATH;
    }
}