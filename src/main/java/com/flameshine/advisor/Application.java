package com.flameshine.advisor;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.SpringApplication;

// TODO: fix the cdk-conversion-lambda errors
// TODO: switch to async approach in JS part
// TODO: add README
// TODO: consider trying AWS Transcribe
// TODO: implement functionality of interacting using voice through the entire application flow
// TODO: add traditional methods of interacting with the web-site

/**
 * Entry point of the application.
 */

@SpringBootApplication
public class Application {

    public static void main(String... args) {
        SpringApplication.run(Application.class, args);
    }
}