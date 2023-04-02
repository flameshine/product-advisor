package com.flameshine.assistant.service;

import java.util.List;
import java.util.Optional;

public interface Matcher<T> {
    Optional<T> match(List<String> keywords);
}