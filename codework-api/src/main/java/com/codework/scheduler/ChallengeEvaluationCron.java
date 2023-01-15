package com.codework.scheduler;

import com.codework.service.IProblemEvaluationService;
import com.codework.utility.DateUtility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class ChallengeEvaluationCron {


    @Autowired
    private IProblemEvaluationService problemEvaluationService;

    @Scheduled(cron = "${judge0.evaluator.cron.expression}")
    public void evaluateChallenge(){
        Date currentDate = DateUtility.currentDate();
        log.debug("EvaluateChallenge cron is running "+ currentDate);
        problemEvaluationService.checkAllPendingSubmissionResult();
        log.debug("EvaluateChallenge cron execution completed ");
    }


}
