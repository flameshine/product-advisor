package com.flameshine.advisor;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.SpringApplication;

// TODO: investigate recognition mismatches (audio files are recorded properly!)
// TODO: try Google Cloud Speech-to-Text (?)

@SpringBootApplication
public class Application {

    public static void main(String... args) {
        SpringApplication.run(Application.class, args);
    }
}