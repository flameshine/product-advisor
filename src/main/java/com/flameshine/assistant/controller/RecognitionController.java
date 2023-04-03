package com.flameshine.assistant.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import lombok.RequiredArgsConstructor;

import com.flameshine.assistant.service.Recognizer;
import com.flameshine.assistant.service.Matcher;
import com.flameshine.assistant.util.Constants;

@Controller
@RequiredArgsConstructor
public class RecognitionController {

    private final Recognizer recognizer;
    private final Matcher<?> retriever;

    @GetMapping({ "/", Constants.RECOGNITION_PATH })
    public String recognize() {
        return Constants.RECOGNITION_PATH;
    }

    @ResponseBody
    @PostMapping(Constants.RECOGNITION_PATH)
    public String recognize(
        @RequestParam("recording") MultipartFile recording
    ) {
        var keywords = recognizer.recognize(recording);

        return retriever.match(keywords)
            .map(Object::toString)
            .orElse("No matching items found");
    }
}