package com.flameshine.advisor.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface Recognizer {
    List<String> recognize(MultipartFile recording);
}