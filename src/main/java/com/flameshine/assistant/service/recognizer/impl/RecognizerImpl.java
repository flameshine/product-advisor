package com.flameshine.assistant.service.recognizer.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UncheckedIOException;

import org.springframework.stereotype.Service;
import edu.cmu.sphinx.api.StreamSpeechRecognizer;
import edu.cmu.sphinx.api.SpeechResult;
import lombok.AllArgsConstructor;

import com.flameshine.assistant.service.recognizer.Recognizer;

@Service
@AllArgsConstructor
public class RecognizerImpl implements Recognizer {

    private final StreamSpeechRecognizer recognizer;

    @Override
    public String recognize(File recording) {

        var result = new StringBuilder();

        try (var stream = new FileInputStream(recording)) {

            recognizer.startRecognition(stream);

            SpeechResult speechResult;

            while ((speechResult = recognizer.getResult()) != null) {
                var hypothesis = speechResult.getHypothesis();
                result.append(hypothesis).append(" ");
            }

        } catch (IOException e) {
            throw new UncheckedIOException(e);
        } finally {
            recognizer.stopRecognition();
        }

        return result.toString();
    }
}