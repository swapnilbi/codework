package com.codework.service;

import com.codework.entity.User;
import com.codework.exception.BusinessException;
import com.codework.model.PasswordChangeInput;

import java.util.List;
import java.util.Optional;

public interface IUserService {

    Optional<User> getUserById(Long userId);

    Optional<User> getUserByUsername(String username);

    User createUser(User user);

    User updateUser(User user);

    List<User> getUsers();

    User enableUser(Long userId);

    User disableUser(Long userId);

    void changePassword(PasswordChangeInput passwordChangeInput, Long userId) throws BusinessException;
}
