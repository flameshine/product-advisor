package com.flameshine.assistant.service;

import java.util.List;

public interface Matcher<T> {
    List<T> match(List<String> keywords);
}