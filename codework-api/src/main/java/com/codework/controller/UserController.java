package com.codework.controller;

import com.codework.entity.User;
import com.codework.exception.BusinessException;
import com.codework.model.PasswordChangeInput;
import com.codework.model.Response;
import com.codework.model.UserProfile;
import com.codework.model.UserRegistrationInput;
import com.codework.service.IUserService;
import com.codework.utility.SecurityHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RequestMapping(value = "api/user")
@RestController
@Slf4j
public class UserController {

	@Autowired
	private IUserService userService;

	@PostMapping
	@PreAuthorize("hasAuthority('ADMIN')")
	public Response<User> createUser(@RequestBody User user) throws BusinessException {
		Optional<User> userOptional = userService.getUserByUsername(user.getUsername().toLowerCase());
		if(userOptional.isPresent()){
			throw new BusinessException("User already exist with username :"+ user.getUsername());
		}
		user = userService.createUser(user);
		return new Response<>(user);
	}

	@PostMapping("register")
	public Response register(@RequestBody UserRegistrationInput registrationInput) throws BusinessException {
		log.info("register user "+registrationInput.getEmail());
		Optional<User> userOptional = userService.getUserByUsername(registrationInput.getEmail().toLowerCase());
		if(userOptional.isPresent()){
			throw new BusinessException("User already registered with email : "+ registrationInput.getEmail());
		}
		userService.registerUser(registrationInput);
		return new Response<>();
	}

	@PutMapping("/{userId}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public Response<User> updateUser(@RequestBody User user) throws BusinessException {
		Optional<User> existingUser = userService.getUserById(user.getId());
		if(!existingUser.isPresent()){
			throw new BusinessException("User does not exist");
		}
		userService.updateUser(user);
		return new Response<>(user);
	}

	@PostMapping("/bulk")
	public Response bulkUserUpload(@RequestParam("file") MultipartFile file) throws BusinessException {
		return userService.bulkUserUpload(file);
	}

	@GetMapping("/{userId}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public Response<User> getUser(@PathVariable Long userId) throws BusinessException {
		User user = userService.getUserById(userId).get();
		user.setPassword(null);
		return new Response<>(user);
	}

	@PutMapping("password/update")
	@PreAuthorize("hasAuthority('ADMIN')")
	public Response changePassword(@RequestBody PasswordChangeInput passwordChangeInput) throws BusinessException {
		userService.changePassword(passwordChangeInput, SecurityHelper.getUserId());
		return new Response<>();
	}

	@PutMapping("/{userId}/password/reset")
	@PreAuthorize("hasAuthority('ADMIN')")
	public Response resetPassword(@PathVariable Long userId, @RequestBody PasswordChangeInput passwordChangeInput) {
		log.info("resetPassword "+userId);
		userService.resetPassword(userId,passwordChangeInput);
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
		log.info("enableUser "+userId);
		User user = userService.enableUser(userId);
		user.setPassword(null);
		return new Response<>(user);
	}

	@GetMapping("/{userId}/disable")
	@PreAuthorize("hasAuthority('ADMIN')")
	public Response<User> disableUser(@PathVariable Long userId) {
		log.info("disableUser "+userId);
		User user = userService.disableUser(userId);
		user.setPassword(null);
		return new Response<>(user);
	}

	@DeleteMapping("/{userId}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public Response deleteUser(@PathVariable Long userId) {
		log.info("deleteUser "+userId);
		userService.deleteUser(userId);
		return new Response<>();
	}

	@GetMapping("/list")
	@PreAuthorize("hasAnyAuthority('ADMIN')")
	public Response<List<User>> getUsers() {
		List<User> users = userService.getUsers();
		return new Response<>(users);
	}

}
