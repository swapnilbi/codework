package com.codework.model;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.codework.entity.Language;
import com.codework.entity.Problem;
import com.codework.entity.ProblemSolution;
import com.codework.enums.ProblemType;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@NoArgsConstructor
public class ProblemDetails {
	private long id;
	@NotNull(message = "challengeId is required")
	@Digits(message = "challengeId is invalid", fraction = 0, integer = 10)
	private long challengeId;
	@NotBlank(message = "name is required")
	private String name;
	@NotBlank(message = "problemStatement is required")
	private String problemStatement;
	@NotNull(message = "type is required")
	private ProblemType type;
	private List<Language> languagesAllowed;
	private ProblemPointSystem pointSystem;
	private Long challengeInstanceId;
	private Map<Integer,String> placeHolderSolution;
	private List<TestCase> testCases;
	private Float memoryLimit;
	private Float cpuLimit;
	private Date createdAt;
	private String createdBy;
	private ProblemSolution problemSolution;
	
	public ProblemDetails(Problem problem){
		this.id = problem.getId();
		this.challengeId = problem.getChallengeId();
		this.name = problem.getName();
		this.problemStatement = problem.getProblemStatement();
		this.pointSystem = problem.getPointSystem();
		this.type = problem.getType();
		this.memoryLimit = problem.getMemoryLimit();
		this.cpuLimit = problem.getCpuLimit();
		this.placeHolderSolution = problem.getPlaceHolderSolution();
		this.testCases = problem.getTestCases();
		this.createdAt = problem.getCreatedAt();
		this.createdBy = problem.getCreatedBy();
	}
}
