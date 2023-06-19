package com.flameshine.advisor.security.impl;

import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import com.google.common.base.Preconditions;
import lombok.RequiredArgsConstructor;

import com.flameshine.advisor.repository.UserRepository;

/**
 * Service that retrieves a user from database by its username and instantiates the {@link UserDetailsImpl} for authentication.
 */

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = repository.findByUsername(username);
        Preconditions.checkState(user.isPresent(), "User with username '%s' not found", username);
        return new UserDetailsImpl(user.get());
    }
}