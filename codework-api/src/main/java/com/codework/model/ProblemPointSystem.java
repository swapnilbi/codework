package com.codework.model;

import lombok.Data;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

@Data
public class ProblemPointSystem {
	@NotNull
	@Digits(fraction = 0, integer = 10)
	private Float correctAnswer;
	private Integer minNumberOfTc;
	private boolean splitPointsByTc;
	private Integer bestSolutionBonus; // execution time
	private Integer quickSolutionBonus; // submission time

}
