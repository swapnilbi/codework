package com.codework.model;

import com.codework.enums.EvaluationStatus;
import lombok.Data;

@Data
public class EvaluationDetails {

	private EvaluationStatus evaluationStatus = EvaluationStatus.IN_PROGRESS;
	private Double points = 0d;

}
