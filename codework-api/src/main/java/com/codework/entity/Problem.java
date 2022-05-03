package com.codework.entity;

import com.codework.enums.ProblemType;
import com.codework.model.ProblemPointSystem;
import com.codework.model.TestCase;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
@Document(collection = "Problem")
public class Problem {
	@Transient
	public static final String SEQUENCE_NAME = "problem_sequence";

	@Id
	private Long id;
	private Long challengeId;
	private Long challengeInstanceId;
	private String name;
	private String problemStatement;
	private ProblemType type;
	private List<Integer> languagesAllowed;
	private Float memoryLimit;
	private Float cpuLimit;
	private Map<Integer,String> placeHolderSolution;
	private List<TestCase> testCases;
	private Date createdAt;
	private String createdBy;
	private ProblemPointSystem pointSystem;

}
