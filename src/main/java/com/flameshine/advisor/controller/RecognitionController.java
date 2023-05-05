package com.flameshine.advisor.controller;

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
import com.flameshine.advisor.util.Constants;

@Controller
@RequestMapping(Constants.RECOGNIZE_PATH)
@RequiredArgsConstructor
public class RecognitionController {

    private final Recognizer recognizer;
    private final Matcher<?> matcher;

    @GetMapping
    public String recognize() {
        return Constants.RECOGNIZE_PATH;
    }

    @PostMapping
    public ModelAndView recognize(
        @RequestParam("recording") MultipartFile recording
    ) {
        var modelAndView = new ModelAndView(Constants.RECOGNIZE_PATH);
        var keywords = recognizer.recognize(recording);
        var matched = matcher.match(keywords);

        if (matched.isEmpty()) {
            return modelAndView.addObject("message", "No matching items found");
        }

        return modelAndView.addObject("matched", matched);
    }
}