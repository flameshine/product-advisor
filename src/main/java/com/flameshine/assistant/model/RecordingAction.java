package com.flameshine.assistant.model;

/**
 * Identifies whether the recording should be started or stopped.
 */
public enum RecordingAction {

    START,
    STOP;

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}