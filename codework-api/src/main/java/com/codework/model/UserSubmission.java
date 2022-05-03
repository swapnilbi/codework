package com.codework.model;

import com.codework.enums.ProblemType;
import com.codework.enums.SubmissionStatus;
import lombok.Data;

import java.util.Date;

@Data
public class UserSubmission {

	private String problem;
	private Long challengeInstanceId;
	private String problemType;
	private Date startTime;
	private Date endTime;
	private Date submittedTime;
	private SubmissionStatus submissionStatus = SubmissionStatus.NOT_STARTED;

}
