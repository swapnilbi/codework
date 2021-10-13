package com.codework.model;

import lombok.Data;

import java.util.List;

@Data
public class ProblemSolutionResult {

	private boolean result;
	private boolean compilationStatus;
	private String compilationLog;
	private String standardOutput;
	private Integer timeLimit;
	private Integer memoryLimit;
	private List<TestCaseResult> testCaseResults;

}
