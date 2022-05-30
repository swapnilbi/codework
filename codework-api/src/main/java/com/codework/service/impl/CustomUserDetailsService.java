package com.codework.service.impl;

import com.codework.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserService userService;

	@Override
	public User loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> userOptional = userService.getUserByUsername(username);
		if (userOptional.isPresent()) {
			return  userOptional.get();
		}
		throw new UsernameNotFoundException("User not found with username: " + username);
	}

}