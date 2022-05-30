package com.codework.service;

import com.codework.entity.UserSession;

import java.util.Optional;

public interface IUserSessionService {

    UserSession createUserSession(UserSession userSession);

    Optional<UserSession> getActiveUserSession(Long userId);

    UserSession logoutUserSession(UserSession userSession);
}
