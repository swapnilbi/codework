package com.codework.entity;

import com.codework.enums.SubmissionStatus;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document(collection = "ChallengeInstanceSubmission")
@ToString
public class ChallengeInstanceSubmission {

	@Transient
	public static final String SEQUENCE_NAME = "challenge_instance_submission_sequence";

	@Id
	private long id;
	private long challengeId;
	private long challengeInstanceId;
	private long userId;
	private SubmissionStatus submissionStatus;
	private Long timeTaken;
	private Date startTime;
	private Date submissionTime;


}
