package com.flameshine.assistant.service.recorder;

public interface Recorder {

    /**
     * Starts the recording.
     * @return A string, specifies the path with the output.
     */
    String start(long recordingTimeMillis);

    /**
     * Stops the recording.
     */
    void stop();
}