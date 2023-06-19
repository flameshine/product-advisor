package com.flameshine.advisor.service;

import java.util.List;

/**
 * Interface that provides methods for matching items from database by received keywords.
 */

@FunctionalInterface
public interface Matcher<T> {
    List<T> match(List<String> keywords);
}