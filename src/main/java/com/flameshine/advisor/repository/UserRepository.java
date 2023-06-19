package com.flameshine.advisor.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flameshine.advisor.entity.User;

/**
 * Spring Data JPA repository for interacting with the {@link User} entity.
 */

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}