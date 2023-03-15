package com.flameshine.assistant.service.recorder;

import java.nio.file.Path;

public interface RecorderService {

    /**
     * Starts the recording.
     * @param attemptIdentifier An attempt identifier, assigned to each individual recording thread.
     * @return A path, specifies the recording result location.
     */
    Path start(String attemptIdentifier);

    /**
     * Stops a certain recording process.
     * @param attemptIdentifier An attempt identifier, used to stop an individual recording thread.
     */
    void stop(String attemptIdentifier);
}