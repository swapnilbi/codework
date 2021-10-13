package com.codework.service;

import com.codework.model.ProblemSolution;
import com.codework.model.ProblemSolutionResult;

import java.util.concurrent.ExecutionException;

public interface IProblemSolutionService {

    ProblemSolutionResult compileSolution(ProblemSolution problemSolution);
}
