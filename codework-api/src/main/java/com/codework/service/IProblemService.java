package com.codework.service;

import com.codework.entity.Problem;
import com.codework.exception.BusinessException;
import com.codework.model.ProblemDetails;

import java.util.List;
import java.util.Optional;

public interface IProblemService {

    Problem getProblem(Long id);

    Problem updateProblem(ProblemDetails problemDetails);

    List<Problem> getProblems(Long challengeInstanceId);

    List<ProblemDetails> getProblems(Long challengeId, Long userId);

    Optional<ProblemDetails> getProblemDetails(Long problemId);

    Problem createProblem(ProblemDetails problem) throws BusinessException;

    void deleteProblem(Long problemId) throws BusinessException;

    void deleteProblems(List<Long> problemIds);
}
