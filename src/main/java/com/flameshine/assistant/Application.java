package com.flameshine.assistant;

import java.io.IOException;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.SpringApplication;

import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import edu.cmu.sphinx.api.Configuration;

// TODO: implement a web part & provision a database

@SpringBootApplication
public class Application {

    private final String acousticModel;
    private final String dictionaryPath;
    private final String grammarPath;
    private final String grammarName;

    @Autowired
    public Application(
        @Value("${recognition.acoustic.model}") String acousticModel,
        @Value("${recognition.dictionary.path}") String dictionaryPath,
        @Value("${recognition.grammar.path}") String grammarPath,
        @Value("${recognition.grammar.name}") String grammarName
    ) {
        this.acousticModel = acousticModel;
        this.dictionaryPath = dictionaryPath;
        this.grammarPath = grammarPath;
        this.grammarName = grammarName;
    }

    public static void main(String... args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public LiveSpeechRecognizer liveSpeechRecognizer() throws IOException {
        return new LiveSpeechRecognizer(configuration());
    }

    @Bean
    public Configuration configuration() {

        var configuration = new Configuration();

        configuration.setAcousticModelPath(acousticModel);
        configuration.setDictionaryPath(dictionaryPath);
        configuration.setGrammarPath(grammarPath);
        configuration.setGrammarName(grammarName);
        configuration.setUseGrammar(true);

        return configuration;
    }
}