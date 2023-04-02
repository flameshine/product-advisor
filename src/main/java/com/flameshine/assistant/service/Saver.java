package com.flameshine.assistant.service;

public interface Saver<T> {
    void save(T entity);
}