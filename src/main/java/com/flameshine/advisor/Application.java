package com.flameshine.advisor;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.SpringApplication;

// TODO: investigate recognition mismatches (look at timeslices and audio recording/converting settings)

@SpringBootApplication
public class Application {

    public static void main(String... args) {
        SpringApplication.run(Application.class, args);
    }
}