package com.codework.entity;

import com.codework.enums.ChallengeStatus;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document(collection = "Challenge")
public class Challenge {

	@Transient
	public static final String SEQUENCE_NAME = "challenge_sequence";

	@Id
	private long id;
	private String name;
	private String shortDescription;
	private String longDescription;
	private Date startDate;
	private Date endDate;
	private String bannerImage;
	private ChallengeStatus status;
	private Date createdAt;
	private Long createdBy;
}
