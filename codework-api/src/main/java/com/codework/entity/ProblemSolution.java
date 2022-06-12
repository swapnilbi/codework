package com.codework.entity;
import com.codework.enums.EvaluationStatus;
import com.codework.enums.SolutionResult;
import com.codework.model.TestCaseResult;
import com.codework.utility.DateUtility;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Data
@Document(collection = "ProblemSolution")
public class ProblemSolution {

	@Transient
	public static final String SEQUENCE_NAME = "problem_solution_sequence";

	@Id
	private long id;
	private Long problemId;
	private Long challengeInstanceId;
	private Long challengeInstanceSubmissionId;
	private Integer languageId;
	private Long userId;
	private String solution;
	private Date createdAt;
	private Date updatedAt;
	private Date submittedAt;
	private EvaluationStatus evaluationStatus;
	private Date evaluationTime;
	private Double avgExecutionTime;
	private Long timeTaken;
	private Float points = 0f;
	private List<TestCaseResult> testCaseResults;
	private String evaluationRemarks;
	private SolutionResult solutionResult;
	private boolean isSubmitted = Boolean.FALSE;
}
