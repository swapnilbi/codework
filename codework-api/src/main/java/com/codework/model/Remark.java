package com.codework.model;

import com.codework.enums.RemarkType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Remark {
	String message;
	RemarkType type;
}


