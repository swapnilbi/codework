package com.codework.service.impl;

import com.codework.config.JwtTokenUtil;
import com.codework.entity.User;
import com.codework.entity.UserSession;
import com.codework.exception.SecurityException;
import com.codework.model.LoginInput;
import com.codework.service.IAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class AuthenticationService implements IAuthenticationService {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private UserSessionService userSessionService;

	@Override
	public String authenticate(LoginInput loginInput) throws SecurityException {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginInput.getUsername(), loginInput.getPassword()));
			final User user = (User) userDetailsService
					.loadUserByUsername(loginInput.getUsername());
			Date issueAt = new Date(System.currentTimeMillis());
			Date expiredAt = new Date(System.currentTimeMillis() + JwtTokenUtil.JWT_TOKEN_VALIDITY*1000);
			String token = jwtTokenUtil.generateToken(user,issueAt,expiredAt);
			UserSession userSession = new UserSession(user.getId(),token,issueAt,expiredAt);
			userSessionService.createUserSession(userSession);
			return token;
		} catch (DisabledException e) {
			throw new SecurityException("Your account has been disabled. Please contact administrator", e);
		} catch (BadCredentialsException e) {
			throw new SecurityException("Invalid username or password", e);
		}
	}

	@Override
	public void logout(Authentication authentication) {
		final User user = (User) userDetailsService
				.loadUserByUsername(authentication.getName());
		Optional<UserSession> userSession = userSessionService.getActiveUserSession(user.getId());
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

}
