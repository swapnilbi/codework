package com.codework.model;

import com.codework.entity.Challenge;
import com.codework.entity.ChallengeInstance;
import com.codework.entity.ChallengeInstanceSubmission;
import com.codework.entity.ChallengeSubscription;
import com.codework.enums.ChallengeStatus;
import com.codework.enums.ProblemType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.Date;
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
