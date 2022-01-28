package com.readingisgood.services;

import com.readingisgood.models.User;

import java.util.Optional;

public interface UserService {
    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    void save(User user);
}
