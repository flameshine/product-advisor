package com.flameshine.assistant.service.recorder.impl;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;

import javax.sound.sampled.*;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import lombok.extern.slf4j.Slf4j;

import com.flameshine.assistant.service.recorder.Recorder;

// TODO: add an ability to construct path based on user data

@Service
@Slf4j
public class RecorderImpl implements Recorder {

    private final TargetDataLine line;

    @Autowired
    public RecorderImpl(AudioFormat format) throws LineUnavailableException {

        var info = new DataLine.Info(TargetDataLine.class, format);

        if (!AudioSystem.isLineSupported(info)) {
            var message = "Unable to open a line for recording";
            log.error(message);
            throw new ExceptionInInitializerError(message);
        }

        this.line = (TargetDataLine) AudioSystem.getLine(info);
    }

    @Override
    public String start(long recordingTimeMillis) {

        var path = "/tmp/recording.wav";

        var recordingThread = new Thread(() -> {
            try (var recordingStream = new AudioInputStream(line)) {
                var output = new File(path);
                AudioSystem.write(recordingStream, AudioFileFormat.Type.WAVE, output);
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        });

        recordingThread.start();

        try {
            Thread.sleep(recordingTimeMillis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }

        return path;
    }

    @Override
    public void stop() {
        line.close();
    }
}