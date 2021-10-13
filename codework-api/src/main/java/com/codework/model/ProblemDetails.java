package com.codework.model;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.codework.entity.Problem;
import com.codework.enums.ProblemType;
import com.codework.entity.TestCase;

import lombok.Data;

@Data
public class ProblemDetails {
	private long id;
	private long challengeId;
	private String name;
	private String problemStatement;
	private ProblemType type;
	private List<String> languagesAllowed;
	private Date startDate;
	private Date endDate;
	private Map<String,String> placeHolderSolution;
	private List<TestCase> testcases;
	private Date createdAt;
	private String createdBy;
	
	public ProblemDetails(Problem problem){
		this.id = problem.getId();
		this.challengeId = problem.getChallengeId();
		this.name = problem.getName();
		this.problemStatement = problem.getProblemStatement();
		this.type = problem.getType();
		this.languagesAllowed = problem.getLanguagesAllowed();
		this.startDate = problem.getStartDate();
		this.endDate = problem.getEndDate();
		this.placeHolderSolution = problem.getPlaceHolderSolution();
		this.testcases = problem.getTestcases();
		this.createdAt = problem.getCreatedAt();
		this.createdBy = problem.getCreatedBy();
	}
}
