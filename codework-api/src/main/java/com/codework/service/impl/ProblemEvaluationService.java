package com.codework.service.impl;

import com.codework.entity.ChallengeInstanceSubmission;
import com.codework.entity.Problem;
import com.codework.entity.ProblemSolution;
import com.codework.enums.EvaluationStatus;
import com.codework.enums.SolutionResult;
import com.codework.model.ProblemPointSystem;
import com.codework.model.SubmissionResult;
import com.codework.model.TestCaseResult;
import com.codework.service.IChallengeInstanceService;
import com.codework.service.IProblemEvaluationService;
import com.codework.service.IProblemService;
import com.codework.service.IProblemSolutionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
@Slf4j
public class ProblemEvaluationService implements IProblemEvaluationService {

    @Autowired
    private IProblemService problemService;

    @Autowired
    private IProblemSolutionService problemSolutionService;

    @Autowired
    private IChallengeInstanceService challengeInstanceService;

    @Override
    public void evaluateChallengeInstance(ChallengeInstanceSubmission challengeInstanceSubmission) {
        List<ProblemSolution> problemSolutionList = problemSolutionService.getProblemSolutions(challengeInstanceSubmission.getUserId(),challengeInstanceSubmission.getChallengeInstanceId());
        if(problemSolutionList!=null && !problemSolutionList.isEmpty()){
            for(ProblemSolution problemSolution : problemSolutionList){
                if(problemSolution.getSolution()==null || problemSolution.getSolution().trim().length() == 0){
                    problemSolution.setPoints(0f);
                    problemSolution.setSolutionResult(SolutionResult.FAIL);
                    problemSolution.setEvaluationStatus(EvaluationStatus.COMPLETED);
                }else{
                    Problem problem = problemService.getProblem(problemSolution.getProblemId());
                    switch(problem.getType()){
                        case PROGRAM:
                            problemSolution = evaluateProgram(problem,problemSolution);
                            break;
                        default:
                            break;
                    }
                }
                if(challengeInstanceSubmission.getSubmissionTime()!=null){
                    problemSolution.setTimeTaken(challengeInstanceSubmission.getSubmissionTime().getTime()-challengeInstanceSubmission.getStartTime().getTime());
                }
                problemSolution.setEvaluationTime(new Date());
                problemSolutionService.updateSolution(problemSolution);
            }
        }
    }

    @Override
    public void checkAllPendingSubmissionResult() {
        List<ProblemSolution> problemSolutionList = problemSolutionService.getProblemSolutions(EvaluationStatus.IN_PROGRESS);
        if(problemSolutionList!=null && !problemSolutionList.isEmpty()){
            for(ProblemSolution problemSolution : problemSolutionList){
                try{
                    Problem problem = problemService.getProblem(problemSolution.getProblemId());
                    checkSubmissionResult(problem,problemSolution);
                }catch(Exception exception){
                    log.error("Exception while checking submission status problemSolutionId="+problemSolution.getId(),exception);
                }
            }
        }
    }

    @Override
    public void checkAllSubmissionResult(Long challengeInstanceId) {
        List<ProblemSolution> problemSolutionList = problemSolutionService.getProblemSolutionsByChallengeInstanceId(EvaluationStatus.IN_PROGRESS, challengeInstanceId);
        if(problemSolutionList!=null && !problemSolutionList.isEmpty()){
            for(ProblemSolution problemSolution : problemSolutionList){
                try{
                    Problem problem = problemService.getProblem(problemSolution.getProblemId());
                    checkSubmissionResult(problem,problemSolution);
                }catch(Exception exception){
                    log.error("Exception while checking submission status "+challengeInstanceId,exception);
                }
            }
        }
    }

    private ProblemSolution evaluateProgram(Problem problem, ProblemSolution problemSolution) {
        try{
            List<SubmissionResult> submissionResults = problemSolutionService.evaluateSolution(problem,problemSolution);
            if(submissionResults!=null && !submissionResults.isEmpty()){
                List<TestCaseResult> testCaseResults = new ArrayList<>();
                for(int i=0;i<submissionResults.size();i++){
                    TestCaseResult testCaseResult = new TestCaseResult();
                    testCaseResult.setId(problem.getTestCases().get(i).getId());
                    testCaseResult.setSubmissionId(submissionResults.get(i).getToken());
                    testCaseResult.setRemark(submissionResults.get(i).getError());
                    testCaseResult.setExpectedOutput(problem.getTestCases().get(i).getExpectedOutput());
                    testCaseResults.add(testCaseResult);
                }
                problemSolution.setTestCaseResults(testCaseResults);
                problemSolution.setEvaluationStatus(EvaluationStatus.IN_PROGRESS);
            }
        }catch (Exception exception){
            log.error("Exception while evaluating problem "+problemSolution.getId(),exception);
            problemSolution.setEvaluationStatus(EvaluationStatus.FAILED);
            problemSolution.setEvaluationRemarks("System Error");
        }
        return problemSolution;
    }

    @Override
    public void checkSubmissionResult(Problem problem, ProblemSolution problemSolution) throws IOException {
        if(problemSolution.getTestCaseResults()!=null && !problemSolution.getTestCaseResults().isEmpty()){
            List<TestCaseResult> testCaseResults = problemSolutionService.evaluateBatchSubmission(problemSolution.getTestCaseResults());
            if(!testCaseResults.isEmpty()){
                long executionCount = testCaseResults.stream().filter(t-> t.getStatusCode()!=null).count();
                problemSolution.setTestCaseResults(testCaseResults);
                if(executionCount == testCaseResults.size()){
                    ProblemPointSystem pointSystem = problem.getPointSystem();
                    long passedTestCases = testCaseResults.stream().filter(t-> t.isStatus()).count();
                    int minNumberOfTc = pointSystem.getMinNumberOfTc()!=null ? pointSystem.getMinNumberOfTc():problem.getTestCases().size();
                    Float pointsForCorrectAnswer = Float.valueOf(pointSystem.getCorrectAnswer()!=null ? pointSystem.getCorrectAnswer() : 100);
                    float points = 0f;
                    if(passedTestCases >= minNumberOfTc){
                        double avgExecutionTime = testCaseResults.stream().filter(t-> t.isStatus()).mapToDouble(t-> Double.valueOf(t.getTime())).average().getAsDouble();
                        if(pointSystem.isSplitPointsByTc()){
                            float pointsPerTestCase = pointsForCorrectAnswer/passedTestCases;
                            points = pointsPerTestCase * passedTestCases;
                        }else {
                            points = pointsForCorrectAnswer;
                        }
                        problemSolution.setAvgExecutionTime(avgExecutionTime);
                        problemSolution.setPoints(Float.valueOf(Math.round(points)));
                        problemSolution.setSolutionResult(SolutionResult.PASS);
                    }else{
                        problemSolution.setPoints(points);
                        problemSolution.setSolutionResult(SolutionResult.FAIL);
                    }
                    problemSolution.setEvaluationStatus(EvaluationStatus.COMPLETED);
                }
                problemSolutionService.updateSolution(problemSolution);
            }
        }
    }

}
