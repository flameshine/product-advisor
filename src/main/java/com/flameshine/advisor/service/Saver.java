package com.flameshine.advisor.service;

/**
 * Interface that provides methods for saving items.
 */

@FunctionalInterface
public interface Saver<T> {
    void save(T entity);
}