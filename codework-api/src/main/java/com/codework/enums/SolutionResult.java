package com.codework.enums;

public enum SolutionResult {
	PASS,FAIL;

	public static SolutionResult getValue(String value){
		for(SolutionResult solutionResult : SolutionResult.values()){
			if(solutionResult.name().equals(value)){
				return solutionResult;
			}
		}
		return null;
	}
}
