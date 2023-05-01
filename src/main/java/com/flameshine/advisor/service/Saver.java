package com.flameshine.advisor.service;

public interface Saver<T> {
    void save(T entity);
}