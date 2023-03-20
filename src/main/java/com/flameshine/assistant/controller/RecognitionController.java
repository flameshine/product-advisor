package com.flameshine.assistant.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import lombok.AllArgsConstructor;

import com.flameshine.assistant.service.recognizer.Recognizer;

@RestController
@AllArgsConstructor
public class RecognitionController {

    private final Recognizer recognizer;

    @PostMapping("/recognize")
    public String recognize(
        @RequestParam("recording") MultipartFile recording
    ) {
        return recognizer.recognize(recording);
    }
}