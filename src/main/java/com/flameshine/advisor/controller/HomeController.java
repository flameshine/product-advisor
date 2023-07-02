package com.flameshine.advisor.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.flameshine.advisor.util.WebPaths;

/**
 * Controller for the "/home" page, which greets the logged user.
 */

@Controller
public class HomeController {

    @GetMapping({ "/", WebPaths.HOME})
    public String home() {
        return WebPaths.HOME;
    }
}