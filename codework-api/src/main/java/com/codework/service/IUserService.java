package com.codework.service;

import com.codework.entity.User;
import com.codework.exception.BusinessException;
import com.codework.model.PasswordChangeInput;
import com.codework.model.Response;
import com.codework.model.UserRegistrationInput;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface IUserService {

    Optional<User> getUserById(Long userId);

    Optional<User> getUserByUsername(String username);

    User createUser(User user) throws BusinessException;

    void registerUser(UserRegistrationInput registrationInput) throws BusinessException;

    User updateUser(User user);

    List<User> getUsers();

    User enableUser(Long userId);

    User disableUser(Long userId);

    void changePassword(PasswordChangeInput passwordChangeInput, Long userId) throws BusinessException;

    Response bulkUserUpload(MultipartFile file) throws BusinessException;

    void resetPassword(Long userId, PasswordChangeInput passwordChangeInput);

    boolean isBetaUser(Long userId);

    void deleteUser(Long userId);
}
