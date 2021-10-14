package com.codework.service;

import com.codework.exception.SystemException;
import com.codework.model.ProblemSolution;
import com.codework.model.ProblemSolutionResult;

import java.util.concurrent.ExecutionException;

public interface IProblemSolutionService {

    ProblemSolutionResult compileSolution(ProblemSolution problemSolution) throws SystemException;

    ProblemSolutionResult runAllTests(ProblemSolution problemSolution);

    void evaluateSolution(ProblemSolution problemSolution);
}
