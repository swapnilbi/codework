package com.codework.model;

import lombok.Data;

@Data
public class ProblemSolution {

	private Long problemId;
	private String languageId;
	private String solution;
	private String stdIn;


}
