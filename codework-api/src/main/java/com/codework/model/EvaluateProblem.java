package com.codework.model;

import com.codework.entity.Language;
import com.codework.entity.ProblemSolution;
import com.codework.enums.ProblemType;
import lombok.Data;

@Data
public class EvaluateProblem {

	private ProblemSolution problemSolution;
	private String name;
	private ProblemType type;
	private Language language;
	private String evaluatedBy;

}
