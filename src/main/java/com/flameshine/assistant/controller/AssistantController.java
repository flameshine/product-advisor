package com.flameshine.assistant.controller;

import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.flameshine.assistant.model.RecordingAction;
import com.flameshine.assistant.service.recorder.Recorder;

// TODO: make parameterizable

@Controller
public class AssistantController {

    private static final String ASSISTANT_PATH = "/assistant";

    private final Recorder recorder;

    @Autowired
    public AssistantController(Recorder recorder) {
        this.recorder = recorder;
    }

    @GetMapping(ASSISTANT_PATH)
    public ModelAndView assistant(@RequestParam("action") RecordingAction action) {

        var result = new ModelAndView(ASSISTANT_PATH);

        if (RecordingAction.START == action) {

            var defaultRecordingTimeMillis = 10000L;
            var path = recorder.start(defaultRecordingTimeMillis);

            return result.addObject("path", path);

        } else if (RecordingAction.STOP == action) {
            recorder.stop();
        }

        return result;
    }
}