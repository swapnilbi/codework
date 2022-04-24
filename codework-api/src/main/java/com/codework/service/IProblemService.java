package com.codework.service;

import java.util.List;
import java.util.Optional;

import com.codework.entity.Problem;
import com.codework.model.ProblemDetails;

public interface IProblemService {

    Optional<ProblemDetails> getProblem(Long id);

    Problem updateProblem(ProblemDetails problemDetails);

    List<ProblemDetails> getProblems(Long challengeId);

    Problem createProblem(ProblemDetails problem);

}
