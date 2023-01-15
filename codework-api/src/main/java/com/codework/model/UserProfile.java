package com.codework.model;

import com.codework.entity.User;
import com.codework.enums.Gender;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class UserProfile {

	private long id;
	private String fullName;
	private String email;
	private Gender gender;
	private List<String> roles;

	public UserProfile(){

	}

	public static UserProfile getUserProfile(User user){
		UserProfile userProfile = new UserProfile();
		userProfile.setId(user.getId());
		userProfile.setFullName(user.getFullName());
		userProfile.setGender(user.getGender());
		return userProfile;
	}

	public UserProfile(User user){
		this.id = user.getId();
		this.fullName = user.getFullName();
		this.email = user.getEmail();
		this.gender = user.getGender();
		this.roles = user.getRoles();
	}

}
