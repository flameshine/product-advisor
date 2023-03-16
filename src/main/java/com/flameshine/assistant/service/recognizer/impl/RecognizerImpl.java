package com.flameshine.assistant.service.recognizer.impl;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import edu.cmu.sphinx.api.StreamSpeechRecognizer;
import edu.cmu.sphinx.api.SpeechResult;
import lombok.extern.slf4j.Slf4j;
import lombok.AllArgsConstructor;

import com.flameshine.assistant.service.recognizer.Recognizer;
import com.flameshine.assistant.util.LoggingUtils;

@Service
@Slf4j
@AllArgsConstructor
public class RecognizerImpl implements Recognizer {

    private final StreamSpeechRecognizer recognizer;

    @Override
    public String recognize(MultipartFile recording) {

        var result = new StringBuilder();

        try (var stream = recording.getInputStream()) {

            recognizer.startRecognition(stream);

            SpeechResult speechResult;

            while ((speechResult = recognizer.getResult()) != null) {
                var hypothesis = speechResult.getHypothesis();
                result.append(hypothesis).append(" ");
            }

        } catch (IOException e) {

            LoggingUtils.logErrorAndThrowRuntimeException(
                String.format("Unable to recognize speech from input file: %s", recording)
            );

        } finally {
            recognizer.stopRecognition();
        }

        return result.toString();
    }
}