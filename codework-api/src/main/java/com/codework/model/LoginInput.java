package com.codework.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginInput {

	@NotBlank
	private String  username;
	@NotBlank
	private String  password;


}
