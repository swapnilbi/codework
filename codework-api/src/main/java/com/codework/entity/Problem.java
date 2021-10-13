package com.codework.entity;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.codework.enums.ProblemType;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "Problem")
public class Problem {
	@Transient
	public static final String SEQUENCE_NAME = "problem_sequence";

	@Id
	private Long id;
	private Long challengeId;
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
}
