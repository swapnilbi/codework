package com.codework.controller;

import com.codework.entity.User;
import com.codework.exception.BusinessException;
import com.codework.model.Response;
import com.codework.model.UserProfile;
import com.codework.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequestMapping(value = "api/user")
@RestController
public class UserController {

	@Autowired
	private IUserService userService;

	@PostMapping
	@PreAuthorize("hasAuthority('ADMIN')")
	public Response<User> createUser(@RequestBody User user) throws BusinessException {
		Optional<User> userOptional = userService.getUserByUsername(user.getUsername());
		if(userOptional.isPresent()){
			throw new BusinessException("User already exist with username :"+ user.getUsername());
		}
		user = userService.createUser(user);
		return new Response<>(user);
	}

	@PutMapping
	@PreAuthorize("hasAuthority('ADMIN')")
	public Response<User> updateUser(@RequestBody User user) {
		User existingUser = userService.getUserById(user.getId());
		userService.updateUser(user);
		return new Response<>(user);
	}

	@GetMapping("/profile")
	@PreAuthorize("hasAnyAuthority('USER','ADMIN')")
	public Response<UserProfile> getUserProfile(Authentication authentication) {
		User user = userService.getUserByUsername(authentication.getName()).get();
		return new Response<>(new UserProfile(user));
	}

}