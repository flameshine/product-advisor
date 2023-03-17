package com.flameshine.assistant.service.converter.impl;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.UUID;
import java.util.function.Supplier;

import javax.sound.sampled.AudioFileFormat;

import com.xuggle.xuggler.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.xuggle.mediatool.ToolFactory;

import com.flameshine.assistant.service.converter.AudioConverter;

// TODO: fix issues

@Service
public class XugglerAudioConverter implements AudioConverter {

    private static final Supplier<String> RECORDING_ID_SUPPLIER = () -> UUID.randomUUID()
        .toString()
        .toLowerCase();

    @Override
    public File convert(MultipartFile file, AudioFileFormat.Type desiredFormat) {

        /**
         * This is a temporary workaround
         */
        var inputPath = String.format(
            "/tmp/recording-%s.webm",
            RECORDING_ID_SUPPLIER.get()
        );

        createFile(inputPath);

        var inputContainer = IContainer.make();
        var inputContainerFormat = IContainerFormat.make();

        inputContainerFormat.setInputFormat(
            inputPath.split("\\.")[1]
        );

        if (inputContainer.open(inputPath, IContainer.Type.READ, inputContainerFormat) < 0) {
            var message = String.format("Error opening input file: %s", inputPath);
            throw new RuntimeException(message);
        }

        var mediaReader = ToolFactory.makeReader(inputPath);

        var outputPath = String.format(
            "/tmp/recording-%s.wav",
            RECORDING_ID_SUPPLIER.get()
        );

        var result = createFile(outputPath);
        var outputContainer = IContainer.make();
        var outputContainerFormat = IContainerFormat.make();

        outputContainerFormat.setOutputFormat(desiredFormat.getExtension(), outputPath, null);

        if (outputContainer.open(outputPath, IContainer.Type.WRITE, outputContainerFormat) < 0) {
            var message = String.format("Error opening output file: %s", outputPath);
            throw new RuntimeException(message);
        }

        var mediaWriter = ToolFactory.makeWriter(outputPath, outputContainer);

        mediaReader.setBufferedImageTypeToGenerate(0);
        mediaReader.getContainer().setForcedAudioCodec(ICodec.ID.CODEC_ID_NONE);

        mediaWriter.addAudioStream(0, 0, 1, 44100);

        try {
            while (mediaReader.readPacket() == null);
        } finally {
            mediaReader.close();
            mediaWriter.close();
        }

        return result;
    }

    private static File createFile(String path) {

        var result = new File(path);

        try {
            result.createNewFile();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

        return result;
    }
}