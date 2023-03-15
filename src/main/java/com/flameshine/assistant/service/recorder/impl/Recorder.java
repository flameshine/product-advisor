package com.flameshine.assistant.service.recorder.impl;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.UUID;
import java.util.function.Supplier;

import javax.sound.sampled.*;

import lombok.extern.slf4j.Slf4j;

import com.flameshine.assistant.util.LoggingUtils;

@Slf4j
public class Recorder implements Supplier<Path> {

    private static final String MICROPHONE_DEVICE_NAME = "Default Audio Device";

    private final AudioFormat format;

    private TargetDataLine line;

    public Recorder(AudioFormat format) {
        this.format = format;
    }

    @Override
    public Path get() {

        var microphone = Arrays.stream(AudioSystem.getMixerInfo())
            .filter(info -> MICROPHONE_DEVICE_NAME.equals(info.getName()))
            .findFirst();

        if (microphone.isEmpty()) {
            LoggingUtils.logErrorAndThrowRuntimeException("Unable to find a suitable microphone device");
        }

        var fileName = String.format(
            "recording-%s.wav",
            UUID.randomUUID().toString().toLowerCase()
        );

        var path = Paths.get("/tmp", fileName);

        try {

            var mixer = AudioSystem.getMixer(microphone.get());
            var info = mixer.getTargetLineInfo()[0];

            if (!AudioSystem.isLineSupported(info)) {
                LoggingUtils.logErrorAndThrowRuntimeException(
                    String.format("Unable to open a line for recording: %s", info)
                );
            }

            line = (TargetDataLine) mixer.getLine(info);

            line.open(format);
            line.start();

            try (var recordingStream = new AudioInputStream(line)) {
                AudioSystem.write(recordingStream, AudioFileFormat.Type.WAVE, path.toFile());
            }

        } catch (LineUnavailableException | IOException e) {
            LoggingUtils.logErrorAndThrowRuntimeException("An unexpected exception has occurred", e);
        }

        return path;
    }

    public void stop() {
        if (line != null) {
            line.stop();
            line.close();
        }
    }
}