package com.codework.model;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.codework.entity.Language;
import com.codework.entity.Problem;
import com.codework.enums.ProblemType;

import lombok.Data;

@Data
public class ProblemDetails {
	private long id;
	private long challengeId;
	private String name;
	private String problemStatement;
	private ProblemType type;
	private List<Language> languagesAllowed;
	private Date startDate;
	private Date endDate;
	private Map<Integer,String> placeHolderSolution;
	private List<TestCase> testcases;
	private Float memoryLimit;
	private Float cpuLimit;
	private Date createdAt;
	private String createdBy;
	
	public ProblemDetails(Problem problem){
		this.id = problem.getId();
		this.challengeId = problem.getChallengeId();
		this.name = problem.getName();
		this.problemStatement = problem.getProblemStatement();
		this.type = problem.getType();
		this.startDate = problem.getStartDate();
		this.endDate = problem.getEndDate();
		this.memoryLimit = problem.getMemoryLimit();
		this.cpuLimit = problem.getCpuLimit();
		this.placeHolderSolution = problem.getPlaceHolderSolution();
		this.testcases = problem.getTestCases();
		this.createdAt = problem.getCreatedAt();
		this.createdBy = problem.getCreatedBy();
	}
}
