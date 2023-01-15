package com.codework.model;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

@Data
@ToString
public class ProblemPointSystem {
	@NotNull
	@Digits(fraction = 0, integer = 10)
	private Integer correctAnswer;
	private Integer minNumberOfTc;
	private boolean splitPointsByTc;
	private Integer bestSolutionBonus; // execution time - TODO
	private Integer quickSolutionBonus; // submission time - TODO

}
