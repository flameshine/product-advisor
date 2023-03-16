package com.flameshine.assistant.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import lombok.AllArgsConstructor;

import com.flameshine.assistant.service.recognizer.Recognizer;

// TODO: make parameterizable

@Controller
@AllArgsConstructor
public class HomeController {

    private static final String HOME_PATH = "/home";

    private final Recognizer recognizer;

    @GetMapping({ "/", HOME_PATH})
    public ModelAndView assistant(
        @RequestParam("recording") MultipartFile recording
    ) {

        var result = new ModelAndView(HOME_PATH);

        result.addObject("result", recognizer.recognize(recording));

        return result;
    }
}