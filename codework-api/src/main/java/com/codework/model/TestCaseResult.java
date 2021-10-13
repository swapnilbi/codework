package com.codework.model;

import lombok.Data;

@Data
public class TestCaseResult {

	private Integer id;
	private String name;
	private boolean status;
	private String input;
	private Integer time;
	private Integer memory;
	private String remark;
	private String expectedOutput;
	private String actualOutput;

}
