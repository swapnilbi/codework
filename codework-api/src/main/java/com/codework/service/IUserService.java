package com.codework.service;

import com.codework.entity.User;

import java.util.Optional;

public interface IUserService {

    User getUserById(Long userId);

    Optional<User> getUserByUsername(String username);

    User createUser(User user);

    User updateUser(User user);
}
