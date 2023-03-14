package com.flameshine.assistant.service.recorder.impl;

import javax.sound.sampled.*;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import lombok.extern.slf4j.Slf4j;

import com.flameshine.assistant.service.recorder.RecorderService;

// TODO: figure out with silence in all voices
// TODO: review exception throwing policy
// TODO: let users record voices concurrently
// TODO: add an ability to construct path based on user data
// TODO: review -> prettify the code

@Service
@Slf4j
public class RecorderServiceImpl implements RecorderService {

    private final String path;
    private final Recorder recorder;

    @Autowired
    public RecorderServiceImpl(AudioFormat format) {
        this.path = "/tmp/recording.wav";
        this.recorder = new Recorder(format, path);
    }

    @Override
    public String start() {

        var recordingThread = new Thread(recorder);

        recordingThread.start();

        return path;
    }

    @Override
    public void stop() {
        recorder.stop();
    }
}