package com.codework.service;

import com.codework.entity.User;
import com.codework.exception.SecurityException;
import com.codework.model.LoginInput;
import org.springframework.security.core.Authentication;

public interface IAuthenticationService {

    String authenticate(LoginInput loginInput) throws SecurityException;

    void logout(Long userId) throws SecurityException;

    boolean validateToken(String token, User user);

    String refreshToken(Long userId);
}
