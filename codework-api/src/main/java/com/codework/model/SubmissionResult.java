package com.codework.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class SubmissionResult {
	String token;
	String error;
	@SerializedName("language_id")
	String[] languageId;
	@SerializedName("wall_time_limit")
	String[] wallTimeLimit;

}


