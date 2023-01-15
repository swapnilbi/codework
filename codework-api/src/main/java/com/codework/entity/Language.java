package com.codework.entity;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@ToString
@Document(collection = "Language")
public class Language {

	@Id
	private Integer id; // judge0 id
	private String name;
	private String editorCode;



}
