package com.codework.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "TestCase")
public class TestCase {
	@Transient
	public static final String SEQUENCE_NAME = "testcase_sequence";

	@Id
	private long id;
	private String input;
	private String expectedOutput;
	private Boolean isSample;
}
