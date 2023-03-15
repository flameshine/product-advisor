package com.flameshine.assistant.util;

import lombok.extern.slf4j.Slf4j;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class LoggingUtils {

    public static void logErrorAndThrowRuntimeException(String message) {
        logErrorAndThrowRuntimeException(message, null);
    }

    public static <T extends Exception> void logErrorAndThrowRuntimeException(String message, T exceptionToPass) {
        log.error(message);
        throw new RuntimeException(message, exceptionToPass);
    }
}