package com.flameshine.advisor;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.SpringApplication;

// TODO: consider delegating speech recognition to AWS Transcribe
// TODO: consider implementing the functionality of interacting using voice through the entire application flow
// TODO: consider adding traditional methods of interacting with the web-site
// TODO: unit tests

/**
 * Entry point of the application.
 */

@SpringBootApplication
public class Application {

    public static void main(String... args) {
        SpringApplication.run(Application.class, args);
    }
}