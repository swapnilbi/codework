package com.codework.model;

import com.codework.enums.EvaluationStatus;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class EvaluationDetails {

	private EvaluationStatus evaluationStatus = EvaluationStatus.IN_PROGRESS;
	private Double points = 0d;

}
