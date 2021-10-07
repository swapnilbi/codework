package com.codework.entity;

import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection = "ChallengeSubscription")
public class ChallengeSubscription {
	
	private Date createdAt;
	private int challengeId;
	private String userId;
}
