package com.codework.model;

import lombok.Data;

@Data
public class ProblemSolution {

	private Long problemId;
	private Integer languageId;
	private String solution;
	private String customInput;
}
