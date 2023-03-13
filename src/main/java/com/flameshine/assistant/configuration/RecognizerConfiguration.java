package com.flameshine.assistant.configuration;

import java.io.IOException;

import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import edu.cmu.sphinx.api.StreamSpeechRecognizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RecognizerConfiguration {

    private final String acousticModel;
    private final String languageModel;
    private final String dictionaryPath;
    private final String grammarPath;
    private final String grammarName;

    @Autowired
    public RecognizerConfiguration(
        @Value("${recognition.acoustic-model}") String acousticModel,
        @Value("${recognition.language-model}") String languageModel,
        @Value("${recognition.dictionary-path}") String dictionaryPath,
        @Value("${recognition.grammar-path}") String grammarPath,
        @Value("${recognition.grammar-name}") String grammarName
    ) {
        this.acousticModel = acousticModel;
        this.languageModel = languageModel;
        this.dictionaryPath = dictionaryPath;
        this.grammarPath = grammarPath;
        this.grammarName = grammarName;
    }

    @Bean
    public StreamSpeechRecognizer liveSpeechRecognizer() throws IOException {
        return new StreamSpeechRecognizer(configuration());
    }

    @Bean
    public edu.cmu.sphinx.api.Configuration configuration() {

        var configuration = new edu.cmu.sphinx.api.Configuration();

        configuration.setAcousticModelPath(acousticModel);
        configuration.setLanguageModelPath(languageModel);
        configuration.setDictionaryPath(dictionaryPath);
        configuration.setGrammarPath(grammarPath);
        configuration.setGrammarName(grammarName);
        configuration.setUseGrammar(true);

        return configuration;
    }
}