package com.flameshine.assistant.service.recognizer;

import org.springframework.web.multipart.MultipartFile;

public interface Recognizer {
    String recognize(MultipartFile recording);
}