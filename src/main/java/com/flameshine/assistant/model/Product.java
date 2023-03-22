package com.flameshine.assistant.model;

import java.math.BigDecimal;

import org.springframework.data.annotation.Id;

public record Product(
    @Id long id,
    String name,
    BigDecimal price,
    String currency,
    String description
) { }