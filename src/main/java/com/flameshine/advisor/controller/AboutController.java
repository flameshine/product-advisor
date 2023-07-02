package com.flameshine.advisor.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.flameshine.advisor.util.WebPaths;

/**
 * Controller for the "/about" page, which represents a brief description of the application itself.
 */

@Controller
public class AboutController {

    @GetMapping("/about")
    public String about() {
        return WebPaths.ABOUT;
    }
}