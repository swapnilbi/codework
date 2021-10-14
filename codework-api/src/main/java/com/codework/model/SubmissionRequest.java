package com.codework.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SubmissionRequest {

	@SerializedName("language_id")
	private Integer languageId;
	@SerializedName("source_code")
	private String sourceCode;
	@SerializedName("expected_output")
	private String expectedOutput;
	@SerializedName("cpu_time_limit")
	private Float cpuTimeLimit;
	@SerializedName("memory_limit")
	private Float memoryLimit;
	@SerializedName("number_of_runs")
	private Integer numberOfRuns;
	private String stdin;

	public SubmissionRequest(Integer languageId,String sourceCode,String stdin){
		this.languageId = languageId;
		this.sourceCode = sourceCode;
		this.stdin = stdin;
	}

	public SubmissionRequest(Integer languageId,String sourceCode,String stdin,String expectedOutput){
		this(languageId,sourceCode,expectedOutput);
		this.expectedOutput =  expectedOutput;
	}

}


