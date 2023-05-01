package com.flameshine.assistant;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.SpringApplication;

// TODO: investigate recognition mismatches (look at timeslices and audio recording/converting settings)
// TODO: add recording visualization
// TODO: add cart and ability to put recommended products in it
// TODO: add "Contacts" pages

@SpringBootApplication
public class Application {

    public static void main(String... args) {
        SpringApplication.run(Application.class, args);
    }
}