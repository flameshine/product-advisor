package com.flameshine.advisor.service.impl;

import java.util.List;
import java.io.IOException;
import java.io.UncheckedIOException;

import org.springframework.stereotype.Service;
import org.springframework.context.annotation.Primary;
import org.springframework.web.multipart.MultipartFile;
import com.google.protobuf.ByteString;
import com.google.cloud.speech.v1.RecognitionAudio;
import com.google.cloud.speech.v1.RecognitionConfig;
import com.google.cloud.speech.v1.SpeechClient;
import com.google.cloud.speech.v1.SpeechRecognitionAlternative;
import lombok.RequiredArgsConstructor;

import com.flameshine.advisor.service.Recognizer;

/**
 * Implementation of the {@link Recognizer} that employs GCP Speech-to-Text service.
 */

@Service
@Primary
@RequiredArgsConstructor
public class GoogleRecognizer implements Recognizer {

    private final SpeechClient speechClient;
    private final RecognitionConfig config;

    @Override
    public List<String> recognize(MultipartFile recording) {

        try {

            var audio = RecognitionAudio.newBuilder()
                .setContent(ByteString.copyFrom(recording.getBytes()))
                .build();

            var response = speechClient.recognize(config, audio);

            return response.getResultsList().stream()
                .flatMap(result -> result.getAlternativesList().stream())
                .map(SpeechRecognitionAlternative::getTranscript)
                .toList();

        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}