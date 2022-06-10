package com.codework.service.impl;

import com.codework.entity.Problem;
import com.codework.entity.ProblemSolution;
import com.codework.enums.EvaluationStatus;
import com.codework.exception.BusinessException;
import com.codework.exception.CodeWorkExceptionHandler;
import com.codework.exception.SystemException;
import com.codework.model.*;
import com.codework.repository.ProblemSolutionRepository;
import com.codework.repository.SequenceGenerator;
import com.codework.service.IChallengeInstanceService;
import com.codework.service.ICodeExecutorService;
import com.codework.service.IProblemService;
import com.codework.service.IProblemSolutionService;
import com.codework.utility.ChallengeUtility;
import com.codework.utility.DateUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ProblemSolutionService implements IProblemSolutionService {

    @Autowired
    private ICodeExecutorService codeExecutorService;

    @Autowired
    private IProblemService problemService;

    @Autowired
    private IChallengeInstanceService challengeInstanceService;

    @Autowired
    private ProblemSolutionRepository problemSolutionRepository;

    @Autowired
    SequenceGenerator sequenceGenerator;

    private static Logger logger = LoggerFactory.getLogger(CodeWorkExceptionHandler.class);


    @Override
    public ProblemSolutionResult compileSolution(ProblemSolutionInput problemSolution, Long userId) throws SystemException, BusinessException, IOException {
        validateSolution(problemSolution, userId);
        ProblemSolutionResult problemSolutionResult = new ProblemSolutionResult();
        ProblemDetails problem = problemService.getProblemDetails(problemSolution.getProblemId()).get();
        problemSolutionResult.setTimeLimit(problem.getCpuLimit());
        problemSolutionResult.setMemoryLimit(problem.getMemoryLimit());
        if(problemSolution.getCustomInput()!=null && !problemSolution.getCustomInput().trim().equals("")){
            evaluateCustomInput(problemSolution,problemSolutionResult);
        }else{
            evaluateSampleTestCases(problemSolution,problem,problemSolutionResult);
        }
        return problemSolutionResult;
    }

    @Override
    public ProblemSolutionResult runAllTests(ProblemSolutionInput problemSolution, Long userId) throws SystemException, BusinessException, IOException {
            validateSolution(problemSolution, userId);
            ProblemSolutionResult problemSolutionResult = new ProblemSolutionResult();
            ProblemDetails problem = problemService.getProblemDetails(problemSolution.getProblemId()).get();
            problemSolutionResult.setTimeLimit(problem.getCpuLimit());
            problemSolutionResult.setMemoryLimit(problem.getMemoryLimit());
            List<TestCase> testCases = problem.getTestCases();
            int passedTestCases = 0;
            for(TestCase testCase : testCases){
                SubmissionRequest submissionRequest = new SubmissionRequest(problemSolution.getLanguageId(),problemSolution.getSolution(),testCase.getInput(),testCase.getExpectedOutput());
                submissionRequest.setMemoryLimit(problem.getMemoryLimit());
                submissionRequest.setCpuTimeLimit(problem.getCpuLimit());
                SubmissionStatus submissionStatus = codeExecutorService.evaluateSubmission(submissionRequest);
                logger.debug(submissionStatus.toString());
                TestCaseResult testCaseResult = getTestCaseResult(submissionStatus,testCase, Boolean.FALSE);
                if(testCaseResult.isStatus()){
                    passedTestCases++;
                }
                if(ChallengeUtility.isCompilationError(submissionStatus.getStatus().getId())){
                    problemSolutionResult.setCompilationLog(submissionStatus.getCompileOutput());
                    problemSolutionResult.setCompilationError(Boolean.TRUE);
                    break; // no need to execute remaining sample test cases
                }else{
                    problemSolutionResult.getTestCaseResults().add(testCaseResult);
                }
            }
            problemSolutionResult.setResult(passedTestCases == testCases.size());
            if(!problemSolutionResult.isResult() && !problemSolutionResult.getTestCaseResults().isEmpty()) {
                List<TestCaseResult> testCaseResults = problemSolutionResult.getTestCaseResults().stream().filter( t-> !t.isStatus()).collect(Collectors.toList());
                for(TestCaseResult testCaseResult : testCaseResults) {
                    if(ChallengeUtility.isRuntimeError(testCaseResult.getStatusCode())) {
                        problemSolutionResult.setRunTimeError(Boolean.TRUE);
                        break;
                    }
                }
            }
            problemSolutionResult.setCompilationError(problemSolutionResult.getTestCaseResults().isEmpty());
            return problemSolutionResult;
    }

    @Override
    public ProblemSolution saveSolution(ProblemSolutionInput problemSolutionInput, Long userId) throws SystemException, BusinessException {
            validateSolution(problemSolutionInput, userId);
            Optional<ProblemSolution> savedSolution = problemSolutionRepository.findByUserIdAndProblemId(userId, problemSolutionInput.getProblemId());
            ProblemSolution problemSolution = null;
            if(savedSolution.isPresent()){
                problemSolution = savedSolution.get();
            }else{
                problemSolution = new ProblemSolution();
                problemSolution.setId(sequenceGenerator.generateSequence(ProblemSolution.SEQUENCE_NAME));
                problemSolution.setCreatedAt(DateUtility.currentDate());
                problemSolution.setProblemId(problemSolutionInput.getProblemId());
                problemSolution.setUserId(userId); // temp
            }
            if(problemSolutionInput.isSubmitted()){
                problemSolution.setSubmittedAt(new Date());
            }
            problemSolution.setChallengeInstanceId(problemSolutionInput.getChallengeInstanceId());
            problemSolution.setSubmitted(problemSolutionInput.isSubmitted());
            problemSolution.setSolution(problemSolutionInput.getSolution());
            problemSolution.setLanguageId(problemSolutionInput.getLanguageId());
            problemSolution.setUpdatedAt(DateUtility.currentDate());
            problemSolutionRepository.save(problemSolution);
            return problemSolution;
    }

    @Override
    public ProblemSolution updateSolution(ProblemSolution problemSolution) throws SystemException {
        problemSolutionRepository.save(problemSolution);
        return problemSolution;
    }

    @Override
    public ProblemSolution submitSolution(ProblemSolutionInput problemSolutionInput, Long userId) throws SystemException, BusinessException {
        validateSolution(problemSolutionInput, userId);
        problemSolutionInput.setSubmitted(true);
        return saveSolution(problemSolutionInput, userId);
    }

    private void validateSolution(ProblemSolutionInput problemSolutionInput, Long userId) throws BusinessException {
        Optional<ProblemSolution> savedSolution = problemSolutionRepository.findByUserIdAndProblemId(userId, problemSolutionInput.getProblemId());
        if(savedSolution.isPresent() && savedSolution.get().isSubmitted()){
            throw new BusinessException("Solution has been already submitted");
        }
    }

    private ProblemSolutionResult evaluateCustomInput(ProblemSolutionInput problemSolution, ProblemSolutionResult problemSolutionResult) throws IOException {
        SubmissionRequest submissionRequest = new SubmissionRequest(problemSolution.getLanguageId(),problemSolution.getSolution(),problemSolution.getCustomInput());
        SubmissionStatus submissionStatus = codeExecutorService.evaluateSubmission(submissionRequest);
        logger.debug(submissionStatus.toString());
        problemSolutionResult.setStatusCode(submissionStatus.getStatus().getId());
        problemSolutionResult.setCustomInput(problemSolution.getCustomInput());
        if(ChallengeUtility.isCompilationError(submissionStatus.getStatus().getId())) {
        	problemSolutionResult.setCompilationLog(submissionStatus.getCompileOutput());
        	problemSolutionResult.setCompilationError(Boolean.TRUE);
        }else if(ChallengeUtility.isRuntimeError(submissionStatus.getStatus().getId())) {
        	problemSolutionResult.setCompilationLog(submissionStatus.getStderr());
        	problemSolutionResult.setStandardOutput(submissionStatus.getStdout());
        	problemSolutionResult.setRunTimeError(Boolean.TRUE);
        }
        problemSolutionResult.setStandardOutput(submissionStatus.getStdout());
        problemSolutionResult.setResult(submissionStatus.getStatus().getId() == 3);
        return problemSolutionResult;
    }

    private ProblemSolutionResult evaluateSampleTestCases(ProblemSolutionInput problemSolution, ProblemDetails problem, ProblemSolutionResult problemSolutionResult) throws IOException {
        List<TestCase> sampleTestCases = problem.getTestCases().stream().filter( t-> t.getIsSample()).collect(Collectors.toList());
        int passedTestCases = 0;
        for(TestCase testCase : sampleTestCases){
            SubmissionRequest submissionRequest = new SubmissionRequest(problemSolution.getLanguageId(),problemSolution.getSolution(),testCase.getInput(),testCase.getExpectedOutput());
            submissionRequest.setMemoryLimit(problem.getMemoryLimit());
            submissionRequest.setCpuTimeLimit(problem.getCpuLimit());
            SubmissionStatus submissionStatus = codeExecutorService.evaluateSubmission(submissionRequest);
            System.out.println(submissionStatus);
            TestCaseResult testCaseResult = getTestCaseResult(submissionStatus,testCase, Boolean.TRUE);
            if(testCaseResult.isStatus()){
            	passedTestCases++;
            }
            if(ChallengeUtility.isCompilationError(submissionStatus.getStatus().getId())){
            	if(ChallengeUtility.isCompilationError(submissionStatus.getStatus().getId())) {
            		problemSolutionResult.setCompilationLog(submissionStatus.getCompileOutput());
            		problemSolutionResult.setCompilationError(Boolean.TRUE);
            	}
            	problemSolutionResult.setStandardOutput(submissionStatus.getStdout());
                break; // no need to execute remaining sample test cases
            }else{
                problemSolutionResult.getTestCaseResults().add(testCaseResult);
            }
        }
        problemSolutionResult.setResult(passedTestCases == sampleTestCases.size());
        if(!problemSolutionResult.isResult() && !problemSolutionResult.getTestCaseResults().isEmpty()) {
        	List<TestCaseResult> testCaseResults = problemSolutionResult.getTestCaseResults().stream().filter( t-> !t.isStatus()).collect(Collectors.toList());
        	for(TestCaseResult testCaseResult : testCaseResults) {
        		if(ChallengeUtility.isRuntimeError(testCaseResult.getStatusCode())) {
            		problemSolutionResult.setCompilationLog(testCaseResult.getStandardError());
            		problemSolutionResult.setStandardOutput(testCaseResult.getActualOutput());
            		problemSolutionResult.setRunTimeError(Boolean.TRUE);
            		break;
            	}
        	}
        }
        problemSolutionResult.setCompilationError(problemSolutionResult.getTestCaseResults().isEmpty());
        return problemSolutionResult;
    }

    private TestCaseResult getTestCaseResult(SubmissionStatus submissionStatus, TestCase testCase, boolean isSample){
        TestCaseResult testCaseResult = new TestCaseResult();
        testCaseResult.setId(testCase.getId());
        testCaseResult.setMemory(submissionStatus.getMemory());
        testCaseResult.setTime(submissionStatus.getTime());
        testCaseResult.setStatus(submissionStatus.getStatus().getId() == 3);
        testCaseResult.setStatusCode(submissionStatus.getStatus().getId());
        if(submissionStatus.getStatus().getId() != 3){
            testCaseResult.setRemark(ChallengeUtility.getSubmissionStatusDescription(submissionStatus.getStatus().getId()));
        }
        if(isSample){
        	testCaseResult.setActualOutput(submissionStatus.getStdout());
        	testCaseResult.setStandardError(submissionStatus.getStderr());
            testCaseResult.setInput(testCase.getInput());
            testCaseResult.setExpectedOutput(testCase.getExpectedOutput());
        }
        return testCaseResult;
    }

    public List<SubmissionResult> evaluateSolution(Problem problem, ProblemSolution problemSolution) throws IOException {
        List<SubmissionRequest> submissionRequests = new ArrayList<>();
        if(problem.getTestCases()!=null && !problem.getTestCases().isEmpty()){
            for(TestCase testCase : problem.getTestCases()){
                SubmissionRequest submissionRequest = new SubmissionRequest(problemSolution.getLanguageId(),problemSolution.getSolution(),testCase.getInput(),testCase.getExpectedOutput());
                submissionRequest.setMemoryLimit(problem.getMemoryLimit());
                submissionRequest.setCpuTimeLimit(problem.getCpuLimit());
                submissionRequests.add(submissionRequest);
            }
            SubmissionBatch submissionBatch = new SubmissionBatch(submissionRequests);
            return codeExecutorService.createSubmissionBatch(submissionBatch);
        }
        return Collections.emptyList();
    }

    @Override
    public Optional<ProblemSolution> getProblemSolution(Long userId, Long problemId) {
        return problemSolutionRepository.findByUserIdAndProblemId(userId,problemId);
    }

    @Override
    public List<ProblemSolution> getProblemSolutions(Long userId, Long challengeInstanceId) {
        return problemSolutionRepository.findByUserIdAndChallengeInstanceId(userId,challengeInstanceId);
    }

    @Override
    public List<ProblemSolution> getProblemSolutions(EvaluationStatus evaluationStatus) {
        return problemSolutionRepository.findByEvaluationStatus(evaluationStatus);
    }

    @Override
    public List<TestCaseResult> evaluateBatchSubmission(List<TestCaseResult> testCases) throws IOException {
        List<String> tokens = testCases.stream().filter(t-> t.getSubmissionId()!=null && t.getStatusCode()==null).map(t-> t.getSubmissionId()).collect(Collectors.toList());
        SubmissionBatchStatus submissionBatchStatus = codeExecutorService.getSubmissionBatchStatus(tokens);
        if(submissionBatchStatus!=null && submissionBatchStatus.getSubmissions()!=null){
            Map<String,SubmissionStatus> submissionStatusResultMap = submissionBatchStatus.getSubmissions().stream().filter(t-> t!=null).collect(Collectors.toMap(SubmissionStatus::getToken, Function.identity()));
            for(TestCaseResult testCaseResult : testCases){
                if(testCaseResult.getStatusCode() == null){
                    SubmissionStatus submissionStatus = submissionStatusResultMap.get(testCaseResult.getSubmissionId());
                    if(submissionStatus!=null){
                        testCaseResult.setMemory(submissionStatus.getMemory());
                        testCaseResult.setTime(submissionStatus.getTime());
                        testCaseResult.setStatus(submissionStatus.getStatus().getId() == 3);
                        testCaseResult.setStatusCode(submissionStatus.getStatus().getId());
                        if(submissionStatus.getStatus().getId() != 3){
                            testCaseResult.setRemark(ChallengeUtility.getSubmissionStatusDescription(submissionStatus.getStatus().getId()));
                        }
                        testCaseResult.setActualOutput(submissionStatus.getStdout());
                        testCaseResult.setStandardError(submissionStatus.getStderr());
                    }
                }
            }
        }
        return testCases;
    }

}
