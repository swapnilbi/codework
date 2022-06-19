package com.codework.model;

import com.codework.enums.RemarkType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Remark {
	String message;
	RemarkType type;
}


