package com.codework.service;

import java.util.List;
import java.util.Optional;

import com.codework.entity.Problem;
import com.codework.model.ProblemDetails;

public interface IProblemService {

    Optional<ProblemDetails> getProblem(long id);

    List<ProblemDetails> getProblems();

    Optional<ProblemDetails> createProblem(ProblemDetails problem);

}
