package com.flameshine.advisor.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import lombok.RequiredArgsConstructor;

import com.flameshine.advisor.entity.User;
import com.flameshine.advisor.repository.UserRepository;
import com.flameshine.advisor.service.Saver;

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