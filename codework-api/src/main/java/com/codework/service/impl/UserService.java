package com.codework.service.impl;

import com.codework.entity.User;
import com.codework.exception.BusinessException;
import com.codework.model.PasswordChangeInput;
import com.codework.repository.SequenceGenerator;
import com.codework.repository.UserRepository;
import com.codework.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	SequenceGenerator sequenceGenerator;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public Optional<User> getUserById(Long userId) {
		return userRepository.findById(userId);
	}

	@Override
	public Optional<User> getUserByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	@Override
	public User createUser(User user) {
		user.setId(sequenceGenerator.generateSequence(User.SEQUENCE_NAME));
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}

	@Override
	public User updateUser(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}

	@Override
	public List<User> getUsers() {
		List<User> userList = userRepository.findAll();
		if(userList!=null){
			userList.forEach(t-> t.setPassword(null));
		}
		return userList;
	}

	@Override
	public User enableUser(Long userId) {
		User user = getUserById(userId).get();
		user.setActive(true);
		userRepository.save(user);
		return user;
	}

	@Override
	public User disableUser(Long userId) {
		User user = getUserById(userId).get();
		user.setActive(false);
		userRepository.save(user);
		return user;
	}

	@Override
	public void changePassword(PasswordChangeInput passwordChangeInput, Long userId) throws BusinessException {
		User user = getUserById(userId).get();
		if(!BCrypt.checkpw(passwordChangeInput.getOldPassword(), user.getPassword())){
			throw new BusinessException("Your old password is incorrect");
		}
		String newPassword = passwordEncoder.encode(passwordChangeInput.getNewPassword());
		user.setPassword(newPassword);
		userRepository.save(user);
	}
}
