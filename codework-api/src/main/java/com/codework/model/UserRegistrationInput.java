package com.codework.model;

import lombok.Data;

@Data
public class UserRegistrationInput {

	private String firstName;
	private String lastName;
	private String email;
	private String password;

}
