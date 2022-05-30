package com.codework.service;

import java.util.List;
import java.util.Optional;

import com.codework.entity.Language;
import com.codework.entity.Problem;
import com.codework.model.ProblemDetails;

public interface IProblemService {

    Optional<ProblemDetails> getProblem(Long id);

    Problem updateProblem(ProblemDetails problemDetails);

    List<Problem> getProblems(Long challengeInstanceId);

    List<ProblemDetails> getProblems(Long challengeId, Long userId);

    Problem createProblem(ProblemDetails problem);

    void deleteProblem(Long problemId);

    List<Language> getLanguages();
}
