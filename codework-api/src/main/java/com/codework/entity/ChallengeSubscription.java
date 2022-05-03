package com.codework.entity;

import java.util.Date;

import com.codework.enums.ChallengeSubscriptionStatus;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "ChallengeSubscription")
public class ChallengeSubscription {
	@Transient
	public static final String SEQUENCE_NAME = "challenge_subscription_sequence";

	@Id
	private Long id;
	@CreatedDate
	private Date createdAt;
	private Long challengeId;
	private Long userId;
	private ChallengeSubscriptionStatus status;
}
