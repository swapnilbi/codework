package com.codework.model;

import com.codework.entity.ChallengeInstance;
import com.codework.entity.ChallengeInstanceSubmission;
import com.codework.enums.ProblemType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LiveChallengeDetails {

	private ChallengeDetails challengeDetails;
	private ChallengeInstance challengeInstance;
	private ChallengeInstanceSubmission challengeInstanceSubmission;
	private List<ProblemType> problemTypes;

}
