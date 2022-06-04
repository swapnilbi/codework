package com.codework.service.impl;

import com.codework.entity.UserSession;
import com.codework.repository.SequenceGenerator;
import com.codework.repository.UserRepository;
import com.codework.repository.UserSessionRepository;
import com.codework.service.IUserService;
import com.codework.service.IUserSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class UserSessionService implements IUserSessionService {

	@Autowired
	UserSessionRepository userSessionRepository;

	@Autowired
	SequenceGenerator sequenceGenerator;


	@Override
	public UserSession createUserSession(UserSession userSession) {
		userSession.setId(sequenceGenerator.generateSequence(UserSession.SEQUENCE_NAME));
		return userSessionRepository.save(userSession);
	}

	@Override
	public Optional<UserSession> getActiveUserSession(Long userId) {
		Optional<UserSession> optionalUserSession = userSessionRepository.findTop1ByUserIdOrderByLoginTimeDesc(userId);
		if(optionalUserSession.isPresent() && optionalUserSession.get().getLogoutTime()==null){
			return optionalUserSession;
		}
		return Optional.empty();
	}

	@Override
	public UserSession logoutUserSession(UserSession userSession) {
		userSession.setLogoutTime(new Date());
		return userSessionRepository.save(userSession);
	}

	@Override
	public void updateUserSession(UserSession userSession) {
		userSessionRepository.save(userSession);
	}
}
