package com.flameshine.assistant.recognizer;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Port;

import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecognizerImpl implements Recognizer {

    private final LiveSpeechRecognizer recognizer;

    @Autowired
    public RecognizerImpl(LiveSpeechRecognizer recognizer) {
        this.recognizer = recognizer;
    }

    @Override
    public String recognize() {

        if (!AudioSystem.isLineSupported(Port.Info.MICROPHONE)) {
            throw new RuntimeException(
                "Unable to start recognition: microphone line isn't supported"
            );
        }

        recognizer.startRecognition(true);

        var result = new StringBuilder();

        while (true) {

            var hypothesis = recognizer.getResult().getHypothesis();

            if (hypothesis.equals("stop")) {
                recognizer.stopRecognition();
                return result.toString();
            }

            result.append(hypothesis).append(" ");
        }
    }
}