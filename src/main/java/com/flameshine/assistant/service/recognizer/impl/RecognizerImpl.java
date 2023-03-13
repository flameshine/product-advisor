package com.flameshine.assistant.service.recognizer.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UncheckedIOException;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import edu.cmu.sphinx.api.StreamSpeechRecognizer;
import edu.cmu.sphinx.api.SpeechResult;
import lombok.extern.slf4j.Slf4j;

import com.flameshine.assistant.service.recognizer.Recognizer;

@Service
@Slf4j
public class RecognizerImpl implements Recognizer {

    private final StreamSpeechRecognizer recognizer;

    @Autowired
    public RecognizerImpl(StreamSpeechRecognizer recognizer) {
        this.recognizer = recognizer;
    }

    @Override
    public String recognize(File input) {

        var result = new StringBuilder();

        try (var stream = new FileInputStream(input)) {

            recognizer.startRecognition(stream);

            SpeechResult speechResult;

            while ((speechResult = recognizer.getResult()) != null) {
                var hypothesis = speechResult.getHypothesis();
                result.append(hypothesis).append(" ");
            }

        } catch (IOException e) {
            var message = String.format("Unable to recognize speech from input file: %s", input);
            log.error(message);
            throw new UncheckedIOException(message, e);
        } finally {
            recognizer.stopRecognition();
        }

        return result.toString();
    }
}