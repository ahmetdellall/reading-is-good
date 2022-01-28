package com.readingisgood.services;

import com.readingisgood.models.ERole;
import com.readingisgood.models.Role;
import com.readingisgood.models.User;

import java.util.Optional;

public interface RoleService {
    Optional<Role> findByName(ERole name);
}
