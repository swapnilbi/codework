package com.codework.service;

import com.codework.entity.ProblemSolution;
import com.codework.exception.BusinessException;
import com.codework.exception.SystemException;
import com.codework.model.ProblemSolutionInput;
import com.codework.model.ProblemSolutionResult;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

public interface IProblemSolutionService {

    ProblemSolutionResult compileSolution(ProblemSolutionInput problemSolution, Long userId) throws SystemException, BusinessException, IOException;

    ProblemSolution saveSolution(ProblemSolutionInput problemSolution, Long userId) throws SystemException, BusinessException;

    ProblemSolution submitSolution(ProblemSolutionInput problemSolution, Long userId) throws SystemException, BusinessException;

    ProblemSolutionResult runAllTests(ProblemSolutionInput problemSolution, Long userId) throws BusinessException, IOException;

    void evaluateSolution(ProblemSolutionInput problemSolution);

    Optional<ProblemSolution> getProblemSolution(Long userId, Long problemId);
}
