package com.codework.model;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ProblemSolutionInput {

	@NotNull
	private Long problemId;
	@NotNull
	private Integer languageId;
	private String solution;
	private String customInput;
	private boolean isSubmitted = Boolean.FALSE;
}
