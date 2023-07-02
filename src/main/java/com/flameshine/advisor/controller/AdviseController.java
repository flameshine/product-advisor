package com.flameshine.advisor.controller;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import lombok.RequiredArgsConstructor;

import com.flameshine.advisor.service.Recognizer;
import com.flameshine.advisor.service.Matcher;
import com.flameshine.advisor.util.WebPaths;

/**
 * Controller for the "/advise" page, that provides functionality of products selection using a voice assistant.
 */

@Controller
@RequestMapping(WebPaths.ADVISE)
@RequiredArgsConstructor
public class AdviseController {

    private final Recognizer recognizer;
    private final Matcher<?> matcher;

    @GetMapping
    public String advise() {
        return WebPaths.ADVISE;
    }

    @PostMapping
    public ModelAndView advise(
        @RequestParam("recording") MultipartFile recording
    ) {
        var modelAndView = new ModelAndView(WebPaths.ADVISE);

        Optional.ofNullable(recording)
            .map(recognizer::recognize)
            .map(matcher::match)
            .ifPresentOrElse(
                matched -> modelAndView.addObject("matched", matched),
                () -> modelAndView.addObject("message", "No matching items found")
            );

        return modelAndView;
    }
}