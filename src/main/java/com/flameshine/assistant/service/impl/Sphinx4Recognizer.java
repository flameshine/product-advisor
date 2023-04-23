package com.flameshine.assistant.service.impl;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.io.IOException;
import java.io.UncheckedIOException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import edu.cmu.sphinx.api.StreamSpeechRecognizer;
import edu.cmu.sphinx.api.SpeechResult;
import lombok.RequiredArgsConstructor;

import com.flameshine.assistant.service.Recognizer;

@Service
@RequiredArgsConstructor
public class Sphinx4Recognizer implements Recognizer {

    private final StreamSpeechRecognizer recognizer;

    @Override
    public List<String> recognize(MultipartFile recording) {

        List<String> keywords = new LinkedList<>();

        try (var stream = recording.getInputStream()) {

            recognizer.startRecognition(stream);

            SpeechResult speechResult;

            while ((speechResult = recognizer.getResult()) != null) {
                keywords.add(speechResult.getHypothesis());
            }

        } catch (IOException e) {
            throw new UncheckedIOException(e);
        } finally {
            recognizer.stopRecognition();
        }

        return Collections.unmodifiableList(keywords);
    }
}