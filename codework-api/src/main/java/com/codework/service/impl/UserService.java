package com.codework.service.impl;

import com.codework.entity.User;
import com.codework.enums.Gender;
import com.codework.exception.BusinessException;
import com.codework.model.PasswordChangeInput;
import com.codework.model.Response;
import com.codework.repository.SequenceGenerator;
import com.codework.repository.UserRepository;
import com.codework.service.IUserService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

	public static String[] TYPE_LIST = { "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", "application/vnd.ms-excel"};

	@Override
	public User createUser(User user) throws BusinessException {
		Optional<User> existingUser =  getUserByUsername(user.getUsername());
		if(existingUser.isPresent()){
			throw new BusinessException("User already exist with username "+user.getUsername());
		}
		user.setId(sequenceGenerator.generateSequence(User.SEQUENCE_NAME));
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}

	@Override
	public User updateUser(User userForm) {
		User user = getUserById(userForm.getId()).get();
		user.setFullName(userForm.getFullName());
		user.setEmail(userForm.getEmail());
		user.setGender(userForm.getGender());
		user.setRoles(userForm.getRoles());
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

	private List<User> getBulkUserList(MultipartFile file) throws BusinessException {
		try {
			InputStream is = file.getInputStream();
			Workbook workbook = new XSSFWorkbook(is);
			Sheet sheet = workbook.getSheetAt(0);
			List<User> userList = new ArrayList<>();
			Iterator<Row> rows = sheet.iterator();
			int rowNumber = 0;
			while (rows.hasNext()) {
				Row currentRow = rows.next();
				// skip header
				if (rowNumber == 0) {
					rowNumber++;
					continue;
				}
				Iterator<Cell> cellsInRow = currentRow.iterator();
				User user = new User();
				int cellIdx = 0;
				while (cellsInRow.hasNext()) {
					Cell currentCell = cellsInRow.next();
					switch (cellIdx) {
						case 0:
							user.setFullName(currentCell.getStringCellValue());
							break;
						case 1:
							user.setUsername(currentCell.getStringCellValue());
							break;
						case 2:
							user.setPassword(currentCell.getStringCellValue());
							break;
						case 3:
							user.setRoles(Arrays.stream(currentCell.getStringCellValue().split(",")).map(t-> t.toUpperCase(Locale.ROOT)).collect(Collectors.toList()));
							break;
						case 4:
							user.setEmail(currentCell.getStringCellValue());
							break;
						case 5:
							if(currentCell.getStringCellValue()!=null && !currentCell.getStringCellValue().equals("")){
								Gender gender = Gender.valueOf(currentCell.getStringCellValue().trim().toUpperCase(Locale.ROOT));
								user.setGender(gender);
							}
							break;
						default:
							break;
					}
					cellIdx++;
				}
				userList.add(user);
			}
			workbook.close();
			return userList;
		}catch(IOException exception){
			throw new BusinessException("Failed to parse excel file");
		}
	}

	@Override
	public Response bulkUserUpload(MultipartFile file) throws BusinessException {
		if(!Arrays.stream(TYPE_LIST).anyMatch(file.getContentType()::equals)){
			throw new BusinessException("Please upload excel file");
		}
		List<User> userList = getBulkUserList(file);
		if(userList.isEmpty()){
			throw new BusinessException("Invalid data");
		}
		Response response = new Response();
		for(User user : userList){
			Optional<User> existingUser =  getUserByUsername(user.getUsername());
			if(existingUser.isPresent()){
				response.addError("User already exist with username "+user.getUsername());
			}
		}
		if(response.getRemarks().isEmpty()){
			for(User user : userList){
				createUser(user);
			}
		}
		return response;
	}

	@Override
	public void resetPassword(Long userId, PasswordChangeInput passwordChangeInput) {
		User user = getUserById(userId).get();
		String newPassword = passwordEncoder.encode(passwordChangeInput.getNewPassword());
		user.setPassword(newPassword);
		userRepository.save(user);
	}
}
