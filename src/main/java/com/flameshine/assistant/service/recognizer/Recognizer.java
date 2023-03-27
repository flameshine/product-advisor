package com.flameshine.assistant.service.recognizer;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface Recognizer {
    List<String> recognize(MultipartFile recording);
}