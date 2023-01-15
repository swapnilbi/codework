package com.codework.model;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class TestCaseResult {

	private String submissionId;
	private Long id;
	private boolean status;
	private Integer statusCode;
	private String input;
	private String time;
	private Long memory;
	private String remark;
	private String expectedOutput;
	private String actualOutput;
	private String standardError;

}
