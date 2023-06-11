package com.flameshine.advisor;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.SpringApplication;

// TODO: document everything
// TODO: employ AWS Lambda to conduct media-files conversion
// TODO: consider switching to AWS Transcribe

@SpringBootApplication
public class Application {

    public static void main(String... args) {
        SpringApplication.run(Application.class, args);
    }
}