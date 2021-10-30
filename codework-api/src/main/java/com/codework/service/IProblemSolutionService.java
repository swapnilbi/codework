package com.codework.service;

import com.codework.entity.ProblemSolution;
import com.codework.exception.BusinessException;
import com.codework.exception.SystemException;
import com.codework.model.ProblemSolutionInput;
import com.codework.model.ProblemSolutionResult;

import java.util.Optional;
import java.util.concurrent.ExecutionException;

public interface IProblemSolutionService {

    ProblemSolutionResult compileSolution(ProblemSolutionInput problemSolution) throws SystemException;

    ProblemSolution saveSolution(ProblemSolutionInput problemSolution) throws SystemException;

    ProblemSolution submitSolution(ProblemSolutionInput problemSolution) throws SystemException, BusinessException;

    ProblemSolutionResult runAllTests(ProblemSolutionInput problemSolution);

    void evaluateSolution(ProblemSolutionInput problemSolution);

    Optional<ProblemSolution> getProblemSolution(String userId, Long problemId);
}
