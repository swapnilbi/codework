package com.codework.entity;

import com.codework.enums.ProblemType;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
@Document(collection = "Language")
public class Language {

	private String id; // judge0 id
	private String name;
	private String editorCode;

}
