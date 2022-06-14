package com.codework.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class PasswordChangeInput {

	@NotBlank
	private String  oldPassword;
	@NotBlank
	private String  newPassword;


}
