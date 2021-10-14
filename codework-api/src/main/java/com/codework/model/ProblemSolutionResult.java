package com.codework.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ProblemSolutionResult {

	private boolean result;
	private boolean compilationStatus;
	private Integer statusCode;
	private String compilationLog;
	private String customInput;
	private String standardOutput;
	private Float timeLimit;
	private Float memoryLimit;
	private List<TestCaseResult> testCaseResults = new ArrayList<>();

}
