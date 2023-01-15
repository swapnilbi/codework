package com.codework.entity;

import com.codework.enums.ChallengeStatus;
import com.codework.model.ChallengeInstruction;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Data
@ToString
@Document(collection = "Challenge")
public class Challenge {

	@Transient
	public static final String SEQUENCE_NAME = "challenge_sequence";

	@Id
	private long id;
	private String name;
	private String shortDescription;
	private String longDescription;
	private String commonInstructions;
	private List<ChallengeInstruction> questionSpecificInstructions;
	private Date startDate;
	private Date endDate;
	private String bannerImage;
	private ChallengeStatus status;
	private Date createdAt;
	private Long createdBy;
}
