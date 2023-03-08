package com.flameshine.assistant.configuration;

import javax.sound.sampled.AudioFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RecorderConfiguration {

    private final float sampleRate;
    private final int sampleRateSizeBits;
    private final int channelCount;

    @Autowired
    public RecorderConfiguration(
        @Value("${audio.format.sample-rate}") float sampleRate,
        @Value("${audio.format.sample-rate-size-bits}") int sampleRateSizeBits,
        @Value("${audio.format.channel-count}") int channelCount
    ) {
        this.sampleRate = sampleRate;
        this.sampleRateSizeBits = sampleRateSizeBits;
        this.channelCount = channelCount;
    }

    @Bean
    public AudioFormat audioFormat() {
        return new AudioFormat(
            sampleRate,
            sampleRateSizeBits,
            channelCount,
            true,
            true
        );
    }
}