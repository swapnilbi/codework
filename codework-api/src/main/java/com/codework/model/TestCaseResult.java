package com.codework.model;

import lombok.Data;

@Data
public class TestCaseResult {

	private Long id;
	private boolean status;
	private Integer statusCode;
	private String input;
	private String time;
	private String memory;
	private String remark;
	private String expectedOutput;
	private String actualOutput;
	private String standardError;

}
