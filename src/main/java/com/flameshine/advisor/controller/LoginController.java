package com.flameshine.advisor.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.flameshine.advisor.util.WebPaths;

/**
 * Controller for the "/login" page, that allows users to log in into their accounts.
 */

@Controller
public class LoginController {

    @GetMapping(WebPaths.LOGIN)
    public String login(Principal principal) {
        return principal != null ? WebPaths.HOME : WebPaths.LOGIN;
    }
}