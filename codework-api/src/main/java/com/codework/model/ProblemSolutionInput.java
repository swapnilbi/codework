package com.codework.model;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ProblemSolutionInput {

	@NotNull
	private Long problemId;
	private Long challengeSolutionId;
	@NotNull
	private Integer languageId;
	private String solution;
	private String customInput;
	private boolean isSubmitted = Boolean.FALSE;
}
