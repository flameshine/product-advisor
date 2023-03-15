package com.flameshine.assistant.service.recorder;

import java.nio.file.Path;

public interface RecorderService {

    /**
     * Starts the recording.
     * @return A path, specifies the recording result location.
     */
    Path start();

    /**
     * Stops the recording.
     */
    void stop();
}