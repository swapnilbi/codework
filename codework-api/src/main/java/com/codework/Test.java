package com.codework;

public class Test {

	public static void main(String[] args) {
		float pointsForCorrectAnswer = 50;
		long passedTestCases = 7;
		float pointsPerTestCase =(float)(pointsForCorrectAnswer/7);
		System.out.println(pointsPerTestCase);
		System.out.println(Math.round(pointsPerTestCase*7));
	}
}
