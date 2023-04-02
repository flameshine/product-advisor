package com.flameshine.assistant.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import lombok.RequiredArgsConstructor;

import com.flameshine.assistant.service.Recognizer;
import com.flameshine.assistant.service.Matcher;
import com.flameshine.assistant.util.Constants;

@RestController
@RequiredArgsConstructor
public class RecognitionController {

    private final Recognizer recognizer;
    private final Matcher<?> retriever;

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