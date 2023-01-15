package com.codework.service;

import com.codework.entity.Problem;
import com.codework.entity.ProblemSolution;
import com.codework.enums.EvaluationStatus;
import com.codework.exception.BusinessException;
import com.codework.exception.SystemException;
import com.codework.model.ProblemSolutionInput;
import com.codework.model.ProblemSolutionResult;
import com.codework.model.SubmissionResult;
import com.codework.model.TestCaseResult;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface IProblemSolutionService {

    ProblemSolutionResult compileSolution(ProblemSolutionInput problemSolution, Long userId) throws SystemException, BusinessException, IOException, InterruptedException;

    ProblemSolution saveSolution(ProblemSolutionInput problemSolution, Long userId) throws SystemException, BusinessException;

    ProblemSolution updateSolution(ProblemSolution problemSolution) throws SystemException;

    ProblemSolution submitSolution(ProblemSolutionInput problemSolution, Long userId) throws SystemException, BusinessException;

    ProblemSolutionResult runAllTests(ProblemSolutionInput problemSolution, Long userId) throws BusinessException, IOException, InterruptedException;

    List<SubmissionResult> evaluateSolution(Problem problem, ProblemSolution problemSolution) throws IOException;

    Optional<ProblemSolution> getProblemSolution(Long userId, Long problemId);

    List<ProblemSolution> getProblemSolutions(Long userId, Long challengeInstanceId); // TODO remove

    List<ProblemSolution> getProblemSolutionsByProblemId(Long problemId);

    ProblemSolution getProblemSolution(Long problemSolutionId);

    List<ProblemSolution> getProblemSolutions(Long challengeInstanceSubmissionId);

    List<ProblemSolution> getProblemSolutionsByChallengeInstanceId(EvaluationStatus evaluationStatus, Long challengeInstanceId);

    List<ProblemSolution> getProblemSolutions(EvaluationStatus evaluationStatus);

    List<TestCaseResult> evaluateBatchSubmission(List<TestCaseResult> testCases) throws IOException;

    void deleteProblemSolution(Long problemSolutionId);
}
