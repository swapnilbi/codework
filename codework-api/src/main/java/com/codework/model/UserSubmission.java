package com.codework.model;

import com.codework.entity.ChallengeInstance;
import com.codework.entity.ChallengeInstanceSubmission;
import com.codework.enums.EvaluationStatus;
import com.codework.enums.SolutionResult;
import com.codework.enums.SubmissionStatus;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
public class UserSubmission {

	private Long id;
	private String problem;
	private Long challengeInstanceId;
	private String problemType;
	private Date startTime;
	private Date endTime;
	private Date submittedTime;
	private SubmissionStatus submissionStatus = SubmissionStatus.NOT_STARTED;
	private EvaluationStatus evaluationStatus;
	private UserProfile userDetails;
	private String evaluationRemarks;
	private SolutionResult solutionResult;
	private Double totalPoints;
	private Long timeTaken;

	public UserSubmission(){

	}

	public UserSubmission(ChallengeInstance challengeInstance){
		this.setProblem(challengeInstance.getName());
		this.setChallengeInstanceId(challengeInstance.getId());
		this.setStartTime(challengeInstance.getStartDate());
		this.setEndTime(challengeInstance.getEndDate());
		this.setProblemType(challengeInstance.getType());
	}

	public UserSubmission(ChallengeInstanceSubmission challengeInstanceSubmission){
		this.id = challengeInstanceSubmission.getId();
		this.challengeInstanceId = challengeInstanceSubmission.getChallengeInstanceId();
		this.submissionStatus = challengeInstanceSubmission.getSubmissionStatus();
		this.startTime = challengeInstanceSubmission.getStartTime();
		this.submittedTime = challengeInstanceSubmission.getSubmissionTime();
		this.timeTaken = challengeInstanceSubmission.getTimeTaken();
	}

}
