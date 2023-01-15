package com.codework.model;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class TestCase {

	private long id;
	private String input;
	private String expectedOutput;
	private Boolean isSample;

}
