package com.codework.model;

import com.codework.entity.Challenge;
import com.codework.entity.ChallengeSubscription;
import com.codework.enums.ChallengeStatus;
import lombok.Data;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotBlank;

@Data
public class ChallengeDetails {

	private Long id;
	@NotBlank
	private String  name;
	private String shortDescription;
	private String longDescription;
	private Date startDate;
	private Date endDate;
	private String bannerImage;
	private boolean isRegistered;
	private ChallengeStatus status;
	private ChallengeSubscription challengeSubscription;
	private List<UserSubmission> userSubmissions;

	public ChallengeDetails(){

	}

	public ChallengeDetails(Challenge challenge){
		this.id = challenge.getId();
		this.name = challenge.getName();
		this.shortDescription = challenge.getShortDescription();
		this.longDescription = challenge.getLongDescription();
		this.startDate = challenge.getStartDate();
		this.endDate = challenge.getEndDate();
		this.bannerImage = challenge.getBannerImage();
		this.status = challenge.getStatus();
	}

}
