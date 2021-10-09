package com.codework.entity;

import java.util.Date;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Data
@Document(collection = "ChallengeSubscription")
public class ChallengeSubscription {
	@Transient
	public static final String SEQUENCE_NAME = "challenge_sub_sequence";

	private long subId;
	@CreatedDate
	private Date createdAt;
	private long challengeId;
	private String userId;
	private SUBSCRIPTION_STATUS status;
}
