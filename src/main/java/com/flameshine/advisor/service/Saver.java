package com.flameshine.advisor.service;

@FunctionalInterface
public interface Saver<T> {
    void save(T entity);
}