package com.flameshine.assistant.service.recognizer;

import java.io.File;

public interface Recognizer {
    String recognize(File recording);
}