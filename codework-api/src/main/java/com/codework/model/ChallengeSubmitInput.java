package com.codework.model;

import lombok.Data;

import java.util.List;

@Data
public class ChallengeSubmitInput {

	Long challengeId;
	List<ProblemSolutionInput> solutionList;

}
