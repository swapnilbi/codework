package com.codework.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "Language")
public class Language {

	private Integer id; // judge0 id
	private String name;
	private String editorCode;

}
