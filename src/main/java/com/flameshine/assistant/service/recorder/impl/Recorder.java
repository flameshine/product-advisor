package com.flameshine.assistant.service.recorder.impl;

import java.io.File;

import javax.sound.sampled.*;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Recorder implements Runnable {

    private final AudioFormat format;
    private final String path;

    private TargetDataLine line;

    public Recorder(AudioFormat format, String path) {
        this.format = format;
        this.path = path;
    }

    @Override
    public void run() {

        var info = new DataLine.Info(TargetDataLine.class, format);

        if (!AudioSystem.isLineSupported(info)) {
            var message = "Unable to open a line for recording";
            log.error(message);
            throw new RuntimeException(message);
        }

        try {

            line = (TargetDataLine) AudioSystem.getLine(info);

            line.open(format);
            line.start();

            try (var recordingStream = new AudioInputStream(line)) {
                var output = new File(path);
                AudioSystem.write(recordingStream, AudioFileFormat.Type.WAVE, output);
            }

        } catch (Exception e) {
            var message = "An unexpected exception has occurred";
            log.error(message, e);
            throw new RuntimeException(message, e);
        }
    }

    public void stop() {
        if (line != null) {
            line.stop();
            line.close();
        }
    }
}