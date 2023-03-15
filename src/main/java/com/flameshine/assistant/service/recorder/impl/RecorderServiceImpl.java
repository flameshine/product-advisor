package com.flameshine.assistant.service.recorder.impl;

import java.nio.file.Path;
import java.util.concurrent.*;

import javax.sound.sampled.AudioFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import com.flameshine.assistant.service.recorder.RecorderService;
import com.flameshine.assistant.util.LoggingUtils;

// TODO: add an ability for concurrent executions

@Service
@Slf4j
public class RecorderServiceImpl implements RecorderService {

    private final Recorder recorder;

    @Autowired
    public RecorderServiceImpl(AudioFormat format) {
        this.recorder = new Recorder(format);
    }

    @Override
    public Path start() {

        Path result = null;

        try {

            result = CompletableFuture.supplyAsync(recorder)
                .get(30, TimeUnit.SECONDS);

        } catch (ExecutionException | InterruptedException e) {
            Thread.currentThread().interrupt();
            LoggingUtils.logErrorAndThrowRuntimeException("Unable to retrieve the recording result path", e);
        } catch (TimeoutException e) {
            LoggingUtils.logErrorAndThrowRuntimeException("Maximum recording time (30 seconds) exceeded", e);
        }

        return result;
    }

    @Override
    public void stop() {
        recorder.stop();
    }
}