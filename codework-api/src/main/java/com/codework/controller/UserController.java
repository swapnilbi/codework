package com.codework.controller;

import com.codework.entity.User;
import com.codework.exception.BusinessException;
import com.codework.model.PasswordChangeInput;
import com.codework.model.Response;
import com.codework.model.UserProfile;
import com.codework.service.IUserService;
import com.codework.utility.SecurityHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
	public Response<User> updateUser(@RequestBody User user) throws BusinessException {
		Optional<User> existingUser = userService.getUserById(user.getId());
		if(!existingUser.isPresent()){
			throw new BusinessException("User does not exist");
		}
		userService.updateUser(user);
		return new Response<>(user);
	}

	@PutMapping("password/update")
	@PreAuthorize("hasAuthority('ADMIN')")
	public Response changePassword(@RequestBody PasswordChangeInput passwordChangeInput) throws BusinessException {
		userService.changePassword(passwordChangeInput, SecurityHelper.getUserId());
		return new Response<>();
	}

	@GetMapping("/profile")
	@PreAuthorize("hasAnyAuthority('USER','ADMIN')")
	public Response<UserProfile> getUserProfile(Authentication authentication) {
		User user = userService.getUserByUsername(authentication.getName()).get();
		return new Response<>(new UserProfile(user));
	}

	@GetMapping("/{userId}/enable")
	@PreAuthorize("hasAuthority('ADMIN')")
	public Response<User> enableUser(@PathVariable Long userId) {
		User user = userService.enableUser(userId);
		user.setPassword(null);
		return new Response<>(user);
	}

	@GetMapping("/{userId}/disable")
	@PreAuthorize("hasAuthority('ADMIN')")
	public Response<User> disableUser(@PathVariable Long userId) {
		User user = userService.disableUser(userId);
		user.setPassword(null);
		return new Response<>(user);
	}

	@GetMapping("/list")
	@PreAuthorize("hasAnyAuthority('ADMIN')")
	public Response<List<User>> getUsers() {
		List<User> users = userService.getUsers();
		return new Response<>(users);
	}

}
