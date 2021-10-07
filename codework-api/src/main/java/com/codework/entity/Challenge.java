package com.codework.entity;

import java.sql.Date;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Data
@Document(collection = "Challenge")
public class Challenge {
	@Id
	private Integer id;
	private String  name;
	private String shortDescription;
	private String longDescription;
	private Date startDate;
	private Date endDate;
	private String bannerImage;
	private ChallengeStatus status;
}
