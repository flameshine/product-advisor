package com.flameshine.assistant.controller;

import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.flameshine.assistant.model.RecordingAction;
import com.flameshine.assistant.service.recorder.RecorderService;

// TODO: make parameterizable

@Controller
public class HomeController {

    private static final String HOME_PATH = "/home";

    private final RecorderService recorderService;

    @Autowired
    public HomeController(RecorderService recorderService) {
        this.recorderService = recorderService;
    }

    @GetMapping({ "/", HOME_PATH})
    public ModelAndView assistant(
        @RequestParam(value = "action", required = false) String action
    ) {

        var result = new ModelAndView(HOME_PATH);

        if (RecordingAction.START.toString().equals(action)) {
            var path = recorderService.start();
            return result.addObject("path", path);
        } else if (RecordingAction.STOP.toString().equals(action)) {
            recorderService.stop();
        }

        return result;
    }
}