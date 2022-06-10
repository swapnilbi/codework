package com.codework.service;

import com.codework.entity.ChallengeInstanceSubmission;
import com.codework.entity.Problem;
import com.codework.entity.ProblemSolution;

import java.io.IOException;

public interface IProblemEvaluationService {

    void evaluateChallengeInstance(ChallengeInstanceSubmission challengeInstanceSubmission);

    void checkAllSubmissionResult() throws IOException;

    void checkSubmissionResult(Problem problem, ProblemSolution problemSolution) throws IOException;
}
