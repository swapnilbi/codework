package com.codework.model;

import com.codework.entity.Challenge;
import com.codework.entity.ChallengeStatus;
import lombok.Data;

import java.sql.Date;

@Data
public class ChallengeDetails {

	private Integer id;
	private String  name;
	private String shortDescription;
	private String longDescription;
	private Date startDate;
	private Date endDate;
	private String bannerImage;
	private boolean isRegistered;
	private ChallengeStatus status;

	ChallengeDetails(){

	}

	ChallengeDetails(Challenge challenge){
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
