package com.flameshine.assistant.service.converter;

import java.io.File;

import javax.sound.sampled.AudioFileFormat;

import org.springframework.web.multipart.MultipartFile;

/**
 * There is no direct possibility to record an audio file in the suitable format (e. g. 'audio/wav'),
 * since the corresponding MIME type isn't supported by most of the browsers.
 * That's why we have to record them in 'audio/webm' and then use this supportive service to convert it to the needed format.
 */
public interface AudioConverter {

    /**
     * Converts provided audio file into another one with the specified format.
     */
    File convert(MultipartFile file, AudioFileFormat.Type desiredFormat);
}