package com.codework.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SubmissionRequest {

	@JsonProperty("language_id")
	private Integer languageId;
	@JsonProperty("source_code")
	private String sourceCode;
	private String stdin;
}


