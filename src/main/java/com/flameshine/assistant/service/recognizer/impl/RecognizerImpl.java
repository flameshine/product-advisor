package com.flameshine.assistant.service.recognizer.impl;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import edu.cmu.sphinx.api.StreamSpeechRecognizer;
import edu.cmu.sphinx.api.SpeechResult;
import lombok.AllArgsConstructor;

import com.flameshine.assistant.service.recognizer.Recognizer;

@Service
@AllArgsConstructor
public class RecognizerImpl implements Recognizer {

    private final StreamSpeechRecognizer recognizer;

    @Override
    public String recognize(MultipartFile recording) {

        List<String> hypotheses = new LinkedList<>();

        try (var stream = recording.getInputStream()) {

            recognizer.startRecognition(stream);

            SpeechResult speechResult;

            while ((speechResult = recognizer.getResult()) != null) {
                hypotheses.add(
                    speechResult.getHypothesis()
                );
            }

        } catch (IOException e) {
            throw new UncheckedIOException(e);
        } finally {
            recognizer.stopRecognition();
        }

        return String.join(" ", hypotheses);
    }
}