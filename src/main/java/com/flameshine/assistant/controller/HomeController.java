package com.flameshine.assistant.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private static final String HOME_PATH = "/home";

    @GetMapping({ "/", HOME_PATH })
    public String home() {
        return HOME_PATH;
    }
}