package com.codework.entity;
import com.codework.utility.DateUtility;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document(collection = "ProblemSolution")
public class ProblemSolution {

	@Transient
	public static final String SEQUENCE_NAME = "problem_solution_sequence";

	@Id
	private long id;

	private Long problemId;
	private Integer languageId;
	private String userId;
	private String solution;
	private Date createdAt;
	private Date updatedAt;
	private Date submittedAt;
	private boolean isSubmitted = Boolean.FALSE;
}
