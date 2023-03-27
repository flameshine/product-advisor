package com.flameshine.assistant.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import lombok.AllArgsConstructor;

import com.flameshine.assistant.model.Product;
import com.flameshine.assistant.service.recognizer.Recognizer;
import com.flameshine.assistant.service.retriever.ProductRetriever;

@RestController
@AllArgsConstructor
public class RecognitionController {

    private final Recognizer recognizer;
    private final ProductRetriever retriever;

    @PostMapping("/recognize")
    public String recognize(
        @RequestParam("recording") MultipartFile recording
    ) {
        var keywords = recognizer.recognize(recording);

        return retriever.retrieve(keywords)
            .map(Product::toString)
            .orElse("No matching products found");
    }
}