package com.flameshine.assistant.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import lombok.RequiredArgsConstructor;

import com.flameshine.assistant.entity.User;
import com.flameshine.assistant.repository.UserRepository;
import com.flameshine.assistant.service.Saver;

@Service
@Transactional
@RequiredArgsConstructor
public class UserSaver implements Saver<User> {

    private final UserRepository repository;
    private final PasswordEncoder encoder;

    @Override
    public void save(User entity) {

        entity.setPassword(
            encoder.encode(entity.getPassword())
        );

        repository.save(entity);
    }
}