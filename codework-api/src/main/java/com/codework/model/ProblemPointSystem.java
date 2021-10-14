package com.codework.model;

import lombok.Data;

@Data
public class ProblemPointSystem {

	private Float correctAnswer;
	private Integer minNumberOfTc;
	private boolean splitPointsByTc;
	private Integer bestSolutionBonus; // execution time
	private Integer quickSolutionBonus; // submission time

}
