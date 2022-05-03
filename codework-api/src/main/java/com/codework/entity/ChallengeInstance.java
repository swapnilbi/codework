package com.codework.entity;

import com.codework.enums.ChallengeInstanceStatus;
import com.codework.enums.ChallengeStatus;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document(collection = "ChallengeInstance")
public class ChallengeInstance {

	@Transient
	public static final String SEQUENCE_NAME = "challenge_instance_sequence";

	@Id
	private long id;
	private long challengeId;
	private String name;
	private String type;
	private Date startDate;
	private Date endDate;
	private ChallengeInstanceStatus instanceStatus = ChallengeInstanceStatus.CREATED;
	private Date createdAt = new Date();
	private Long createdBy;
}
