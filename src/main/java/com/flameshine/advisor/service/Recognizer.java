package com.flameshine.advisor.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

/**
 * Interface that provides methods for recognizing keywords from received recording.
 */

@FunctionalInterface
public interface Recognizer {
    List<String> recognize(MultipartFile recording);
}