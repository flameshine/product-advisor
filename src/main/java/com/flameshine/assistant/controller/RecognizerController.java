package com.flameshine.assistant.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import lombok.AllArgsConstructor;

import com.flameshine.assistant.service.recognizer.Recognizer;

@Controller
@AllArgsConstructor
public class RecognizerController {

    private static final String RESULT_PATH = "/result";

    private final Recognizer recognizer;

    @GetMapping(RESULT_PATH)
    public String result() {
        return RESULT_PATH;
    }

    @PostMapping("/recognize")
    public ModelAndView recognize(
        @RequestParam("recording") MultipartFile recording
    ) {
        return new ModelAndView(RESULT_PATH)
            .addObject("result", recognizer.recognize(recording));
    }
}