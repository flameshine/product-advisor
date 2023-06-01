package com.flameshine.advisor.service;

import java.util.List;

@FunctionalInterface
public interface Matcher<T> {
    List<T> match(List<String> keywords);
}