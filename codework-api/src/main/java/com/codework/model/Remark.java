package com.codework.model;

import com.codework.enums.RemarkType;
import lombok.Data;

@Data
public class Remark {
	String message;
	RemarkType type;
}


