package com.codework.task;

import com.codework.entity.ChallengeInstanceSubmission;
import com.codework.service.IProblemEvaluationService;

public class ProblemEvaluationTask implements Runnable {

    IProblemEvaluationService problemEvaluationService;

    private ChallengeInstanceSubmission challengeInstanceSubmission;

    public ProblemEvaluationTask(IProblemEvaluationService problemEvaluationService, ChallengeInstanceSubmission challengeInstanceSubmission){
        this.problemEvaluationService = problemEvaluationService;
        this.challengeInstanceSubmission = challengeInstanceSubmission;
    }

    @Override
    public void run() {
        problemEvaluationService.evaluateChallengeInstance(challengeInstanceSubmission);
    }


}
