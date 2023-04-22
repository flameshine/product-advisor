package com.flameshine.assistant.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import lombok.RequiredArgsConstructor;

import com.flameshine.assistant.service.Recognizer;
import com.flameshine.assistant.service.Matcher;
import com.flameshine.assistant.util.Constants;

@Controller
@RequestMapping(Constants.RECOGNITION_PATH)
@RequiredArgsConstructor
public class RecognitionController {

    private final Recognizer recognizer;
    private final Matcher<?> matcher;

    @GetMapping
    public String recognize() {
        return Constants.RECOGNITION_PATH;
    }

    @PostMapping
    public ModelAndView recognize(
        @RequestParam("recording") MultipartFile recording
    ) {
        var modelAndView = new ModelAndView(Constants.RECOGNITION_PATH);
        var keywords = recognizer.recognize(recording);
        var matched = matcher.match(keywords);
        var result = matched.isEmpty() ? "No matching items found" : matched;

        return modelAndView.addObject("result", result);
    }
}