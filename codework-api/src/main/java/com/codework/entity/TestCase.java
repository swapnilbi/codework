package com.codework.entity;

import lombok.Data;

@Data
public class TestCase {

	private long id;
	private String input;
	private String expectedOutput;
	private Boolean isSample;

}
