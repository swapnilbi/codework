package com.codework.model;

import com.codework.enums.ProblemType;
import lombok.Data;

@Data
public class ChallengeInstruction {

	private ProblemType problemType;
	private String instruction;

}
