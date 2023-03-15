package com.flameshine.assistant.model;

import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.context.WebApplicationContext;

/**
 * A supportive class, used to share the recording attempt identifier across the session.
 */
@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class RecordingContext {

    private final String recordingAttemptIdentifier;

    public RecordingContext() {
        this.recordingAttemptIdentifier = UUID.randomUUID().toString().toLowerCase();
    }

    public String recordingAttemptIdentifier() {
        return recordingAttemptIdentifier;
    }
}