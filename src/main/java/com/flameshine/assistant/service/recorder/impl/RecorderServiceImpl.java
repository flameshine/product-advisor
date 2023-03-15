package com.flameshine.assistant.service.recorder.impl;

import java.nio.file.Path;
import java.util.Map;
import java.util.concurrent.*;

import javax.sound.sampled.AudioFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import com.flameshine.assistant.service.recorder.RecorderService;
import com.flameshine.assistant.util.LoggingUtils;

/**
 * Warning:
 *
 * At the moment the created functionality doesn't work since we're sharing data between multiple browser tabs.
 * The problem is we need to start an individual recording thread and let other users to do the same by their own,
 * when being able to stop the recording for a certain user if needed.
 * For this reason those fancy recording identifiers were introduced, but since, as I already mentioned,
 * the data is shared across multiple tabs we're not able to tie an individual recorder and its thread properly.
 */

@Service
@Slf4j
public class RecorderServiceImpl implements RecorderService {

    /**
     * This looks so ugly, so it seems like Satan himself has come and written this logic.
     * It should definitely be re-worked soon-ish.
     */
    private static final Map<String, Thread> ATTEMPT_IDENTIFIER_TO_THREAD = new ConcurrentHashMap<>();

    private final AudioFormat format;

    @Autowired
    public RecorderServiceImpl(AudioFormat format) {
        this.format = format;
    }

    @Override
    public Path start(String attemptIdentifier) {

        var recordingFutureTask = new FutureTask<>(
            new Recorder(format, attemptIdentifier)
        );

        var recordingThread = new Thread(recordingFutureTask);

        ATTEMPT_IDENTIFIER_TO_THREAD.put(attemptIdentifier, recordingThread);

        recordingThread.start();

        Path result = null;

        try {
            result = recordingFutureTask.get(60, TimeUnit.SECONDS);
        } catch (ExecutionException | InterruptedException e) {
            Thread.currentThread().interrupt();
            LoggingUtils.logErrorAndThrowRuntimeException("Unable to retrieve the recording result path", e);
        } catch (TimeoutException e) {
            LoggingUtils.logErrorAndThrowRuntimeException("Maximum recording time (30 seconds) exceeded", e);
        }

        return result;
    }

    @Override
    public void stop(String attemptIdentifier) {
        ATTEMPT_IDENTIFIER_TO_THREAD.get(attemptIdentifier).interrupt();
    }
}