package com.codework.model;

import com.codework.enums.SolutionResult;
import lombok.Data;

import java.util.Date;

@Data
public class BulkUploadSubmission {

	private String username;
	private Long timeTaken;
	private Date startTime;
	private Date submissionTime;
	private String solution;
	private SolutionResult solutionResult;
	private Float points;
	private String remarks;

}
