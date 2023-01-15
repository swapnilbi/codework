package com.codework.task;

import com.codework.entity.ChallengeInstanceSubmission;
import com.codework.service.IProblemEvaluationService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ProblemEvaluationTask implements Runnable {

    IProblemEvaluationService problemEvaluationService;

    private ChallengeInstanceSubmission challengeInstanceSubmission;

    public ProblemEvaluationTask(IProblemEvaluationService problemEvaluationService, ChallengeInstanceSubmission challengeInstanceSubmission){
        this.problemEvaluationService = problemEvaluationService;
        this.challengeInstanceSubmission = challengeInstanceSubmission;
    }

    @Override
    public void run() {
        log.info("Solution submitted for the evaluation "+challengeInstanceSubmission.getId());
        problemEvaluationService.evaluateChallengeInstance(challengeInstanceSubmission);
    }


}
