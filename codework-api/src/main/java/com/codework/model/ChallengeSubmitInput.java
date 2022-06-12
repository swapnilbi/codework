package com.codework.model;

import lombok.Data;

import java.util.List;

@Data
public class ChallengeSubmitInput {

	private Long challengeInstanceId;
	private Long challengeInstanceSubmissionId;
	private List<ProblemSolutionInput> solutionList;

}
