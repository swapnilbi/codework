package com.codework.model;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Data
@ToString
public class ProblemSolutionInput {

	@NotNull
	private Long problemId;
	private Long challengeInstanceId;
	private Long challengeInstanceSubmissionId;
	@NotNull
	private Integer languageId;
	private String solution;
	private String customInput;
	private boolean isSubmitted = Boolean.FALSE;
}
