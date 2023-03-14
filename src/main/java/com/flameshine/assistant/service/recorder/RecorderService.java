package com.flameshine.assistant.service.recorder;

public interface RecorderService {

    /**
     * Starts the recording.
     * @return A string, specifies the path with the output.
     */
    String start();

    /**
     * Stops the recording.
     */
    void stop();
}