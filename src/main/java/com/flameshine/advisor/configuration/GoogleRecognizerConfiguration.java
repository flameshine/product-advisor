package com.flameshine.advisor.configuration;

import java.io.IOException;
import java.io.UncheckedIOException;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.speech.v1.SpeechSettings;
import com.google.cloud.speech.v1.SpeechClient;
import com.google.cloud.speech.v1.RecognitionConfig;

/**
 * Configuration class for GCP Speech-to-Text client.
 */

@Configuration
public class GoogleRecognizerConfiguration {

    private final Integer audioChannelCount;
    private final String credentialsPath;
    private final String languageCode;

    @Autowired
    public GoogleRecognizerConfiguration(
        @Value("${recognizer.google.audio-channel-count}") Integer audioChannelCount,
        @Value("${recognizer.google.credentials}") String credentialsPath,
        @Value("${recognizer.google.language-code}") String languageCode
    ) {
        this.audioChannelCount = audioChannelCount;
        this.credentialsPath = credentialsPath;
        this.languageCode = languageCode;
    }

    @Bean
    public SpeechClient speechClient() {

        try (var stream = new ClassPathResource(credentialsPath).getInputStream()) {

            var credentialsProvider = FixedCredentialsProvider.create(
                ServiceAccountCredentials.fromStream(stream)
            );

            var settings = SpeechSettings.newBuilder()
                .setCredentialsProvider(credentialsProvider)
                .build();

            return SpeechClient.create(settings);

        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Bean
    public RecognitionConfig recognitionConfig() {
        return RecognitionConfig.newBuilder()
            .setAudioChannelCount(audioChannelCount)
            .setLanguageCode(languageCode)
            .build();
    }
}