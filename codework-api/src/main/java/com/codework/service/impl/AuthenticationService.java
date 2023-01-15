package com.codework.service.impl;

import com.codework.config.JwtTokenUtil;
import com.codework.entity.User;
import com.codework.entity.UserSession;
import com.codework.exception.SecurityException;
import com.codework.model.LoginInput;
import com.codework.service.IAuthenticationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
@Slf4j
public class AuthenticationService implements IAuthenticationService {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private UserService userService;

	@Autowired
	private UserSessionService userSessionService;

	@Override
	public String authenticate(LoginInput loginInput) throws SecurityException {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginInput.getUsername().toLowerCase(), loginInput.getPassword()));
			final User user = userService.getUserByUsername(loginInput.getUsername().toLowerCase()).get();
			UserSession userSession = createUserSession(user);
			userSessionService.createUserSession(userSession);
			return userSession.getToken();
		} catch (DisabledException e) {
			log.info("Account is disabled "+loginInput.getUsername());
			throw new SecurityException("Your account is disabled. Please contact administrator", e);
		} catch (BadCredentialsException e) {
			throw new SecurityException("Invalid username or password", e);
		}
	}

	private UserSession createUserSession(User user){
		Date issueAt = new Date(System.currentTimeMillis());
		Date expiredAt = new Date(System.currentTimeMillis() + JwtTokenUtil.JWT_TOKEN_VALIDITY*1000);
		String token = jwtTokenUtil.generateToken(user,issueAt,expiredAt);
		return new UserSession(user.getId(),token,issueAt,expiredAt);
	}

	@Override
	public void logout(Long userId) {
		Optional<UserSession> userSession = userSessionService.getActiveUserSession(userId);
		if(userSession.isPresent()){
			userSessionService.logoutUserSession(userSession.get());
		}
	}

	@Override
	public boolean validateToken(String token, User user) {
		if(jwtTokenUtil.validateToken(token, user)){
			Optional<UserSession> userSession = userSessionService.getActiveUserSession(user.getId());
			if(userSession.isPresent() || (userSession.isPresent() && userSession.get().getLogoutTime()==null)){
				return true;
			}
		}
		return false;
	}

	@Override
	public String refreshToken(Long userId) {
		final User user = userService.getUserById(userId).get();
		UserSession userSession = createUserSession(user);
		Optional<UserSession> existingSession = userSessionService.getActiveUserSession(userSession.getUserId());
		if(existingSession.isPresent()){
			userSession.setId(existingSession.get().getId());
			userSession.setLoginTime(existingSession.get().getLoginTime());
			userSessionService.updateUserSession(userSession);
		}else{
			userSessionService.createUserSession(userSession);
		}
		return userSession.getToken();
	}

}
