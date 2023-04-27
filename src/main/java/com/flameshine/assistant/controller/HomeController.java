package com.flameshine.assistant.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.flameshine.assistant.util.Constants;

@Controller
public class HomeController {

    @GetMapping({ "/", Constants.HOME_PATH })
    public String home() {
        return Constants.HOME_PATH;
    }
}