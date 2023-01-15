package com.codework.model;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class ChallengeSubmitInput {

	private Long challengeInstanceId;
	private Long challengeInstanceSubmissionId;
	private List<ProblemSolutionInput> solutionList;

}
