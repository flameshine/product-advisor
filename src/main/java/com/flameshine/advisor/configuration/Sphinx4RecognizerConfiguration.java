package com.flameshine.advisor.configuration;

import java.io.IOException;

import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import edu.cmu.sphinx.api.StreamSpeechRecognizer;

/**
 * Configuration for Sphinx4 recognizer.
 * @deprecated Use {@link GoogleRecognizerConfiguration} with the corresponding recognizer, as it provides significantly higher accuracy.
 */

@Configuration
@Deprecated
public class Sphinx4RecognizerConfiguration {

    private final Integer sampleRate;
    private final String acousticModel;
    private final String languageModel;
    private final String dictionaryPath;
    private final String grammarPath;
    private final String grammarName;

    @Autowired
    public Sphinx4RecognizerConfiguration(
        @Value("${recognizer.sphinx4.sample-rate}") Integer sampleRate,
        @Value("${recognizer.sphinx4.acoustic-model}") String acousticModel,
        @Value("${recognizer.sphinx4.language-model}") String languageModel,
        @Value("${recognizer.sphinx4.dictionary-path}") String dictionaryPath,
        @Value("${recognizer.sphinx4.grammar-path}") String grammarPath,
        @Value("${recognizer.sphinx4.grammar-name}") String grammarName
    ) {
        this.sampleRate = sampleRate;
        this.acousticModel = acousticModel;
        this.languageModel = languageModel;
        this.dictionaryPath = dictionaryPath;
        this.grammarPath = grammarPath;
        this.grammarName = grammarName;
    }

    @Bean
    public StreamSpeechRecognizer liveSpeechRecognizer() throws IOException {

        var configuration = new edu.cmu.sphinx.api.Configuration();

        configuration.setSampleRate(sampleRate);
        configuration.setAcousticModelPath(acousticModel);
        configuration.setLanguageModelPath(languageModel);
        configuration.setDictionaryPath(dictionaryPath);
        configuration.setGrammarPath(grammarPath);
        configuration.setGrammarName(grammarName);
        configuration.setUseGrammar(true);

        return new StreamSpeechRecognizer(configuration);
    }
}