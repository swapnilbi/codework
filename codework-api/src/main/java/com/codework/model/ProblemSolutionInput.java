package com.codework.model;

import lombok.Data;

@Data
public class ProblemSolutionInput {

	private Long problemId;
	private Integer languageId;
	private String solution;
	private String customInput;
	private boolean isSubmitted = Boolean.FALSE;
}
