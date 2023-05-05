package com.flameshine.advisor.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.flameshine.advisor.util.Constants;

@Controller
public class AboutController {

    @GetMapping("/about")
    public String about() {
        return Constants.ABOUT_PATH;
    }
}