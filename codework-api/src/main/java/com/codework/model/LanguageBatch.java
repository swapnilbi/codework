package com.codework.model;

import com.codework.entity.Language;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class LanguageBatch {

	List<Language> languages;

}
