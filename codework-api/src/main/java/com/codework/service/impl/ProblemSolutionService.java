package com.codework.service.impl;

import com.codework.entity.ProblemSolution;
import com.codework.exception.BusinessException;
import com.codework.exception.SystemException;
import com.codework.model.*;
import com.codework.repository.ProblemSolutionRepository;
import com.codework.repository.SequenceGenerator;
import com.codework.service.ICodeExecutorService;
import com.codework.service.IProblemService;
import com.codework.service.IProblemSolutionService;
import com.codework.utility.ChallengeUtility;
import com.codework.utility.DateUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

@Service
public class ProblemSolutionService implements IProblemSolutionService {

    @Autowired
    private ICodeExecutorService codeExecutorService;

    @Autowired
    private IProblemService problemService;

    @Autowired
    private ProblemSolutionRepository problemSolutionRepository;

    @Autowired
    SequenceGenerator sequenceGenerator;


    @Override
    public ProblemSolutionResult compileSolution(ProblemSolutionInput problemSolution) throws SystemException {
        try{
            ProblemSolutionResult problemSolutionResult = new ProblemSolutionResult();
            ProblemDetails problem = problemService.getProblem(problemSolution.getProblemId()).get();
            problemSolutionResult.setTimeLimit(problem.getCpuLimit());
            problemSolutionResult.setMemoryLimit(problem.getMemoryLimit());
            if(problemSolution.getCustomInput()!=null && !problemSolution.getCustomInput().trim().equals("")){
                evaluateCustomInput(problemSolution,problemSolutionResult);
            }else{
                evaluateSampleTestCases(problemSolution,problem,problemSolutionResult);
            }
            return problemSolutionResult;
        }catch(Exception e){
        	e.printStackTrace();
            throw new SystemException("Exception in compile solution");
        }
    }

    @Override
    public ProblemSolution saveSolution(ProblemSolutionInput problemSolutionInput) throws SystemException {
        try{
            Optional<ProblemSolution> savedSolution = problemSolutionRepository.findByUserIdAndProblemId("1", problemSolutionInput.getProblemId());
            ProblemSolution problemSolution = null;
            if(savedSolution.isPresent()){
                problemSolution = savedSolution.get();
            }else{
                problemSolution = new ProblemSolution();
                problemSolution.setId(sequenceGenerator.generateSequence(ProblemSolution.SEQUENCE_NAME));
                problemSolution.setCreatedAt(DateUtility.currentDate());
                problemSolution.setProblemId(problemSolutionInput.getProblemId());
                problemSolution.setUserId("1"); // temp
            }
            problemSolution.setSolution(problemSolutionInput.getSolution());
            problemSolution.setLanguageId(problemSolutionInput.getLanguageId());
            problemSolution.setSubmitted(problemSolutionInput.isSubmitted());
            if(problemSolutionInput.isSubmitted()){
                problemSolution.setSubmittedAt(DateUtility.currentDate());
            }
            problemSolution.setUpdatedAt(DateUtility.currentDate());
            problemSolutionRepository.save(problemSolution);
            return problemSolution;
        }catch(Exception e){
            e.printStackTrace();
            throw new SystemException("Exception in compile solution");
        }
    }

    @Override
    public ProblemSolution submitSolution(ProblemSolutionInput problemSolutionInput) throws SystemException, BusinessException {
        Optional<ProblemSolution> savedSolution = problemSolutionRepository.findByUserIdAndProblemId("1", problemSolutionInput.getProblemId());
        if(savedSolution.isPresent() && savedSolution.get().isSubmitted()){
            throw new BusinessException("Solution has been already submitted");
        }
        problemSolutionInput.setSubmitted(Boolean.TRUE);
        return saveSolution(problemSolutionInput);
    }

    private ProblemSolutionResult evaluateCustomInput(ProblemSolutionInput problemSolution, ProblemSolutionResult problemSolutionResult) throws IOException {
        SubmissionRequest submissionRequest = new SubmissionRequest(problemSolution.getLanguageId(),problemSolution.getSolution(),problemSolution.getCustomInput());
        SubmissionStatus submissionStatus = codeExecutorService.evaluateSubmission(submissionRequest);
        System.out.println(submissionStatus);
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
        if(submissionStatus.getStatus().getId() != 3){
            testCaseResult.setRemark(ChallengeUtility.getSubmissionStatusDescription(submissionStatus.getStatus().getId()));
        }
        if(isSample){
        	testCaseResult.setStatusCode(submissionStatus.getStatus().getId());
        	testCaseResult.setActualOutput(submissionStatus.getStdout());
        	testCaseResult.setStandardError(submissionStatus.getStderr());
            testCaseResult.setInput(testCase.getInput());
            testCaseResult.setExpectedOutput(testCase.getExpectedOutput());
        }
        return testCaseResult;
    }

    @Override
    public ProblemSolutionResult runAllTests(ProblemSolutionInput problemSolution) {
        try{
            ProblemSolutionResult problemSolutionResult = new ProblemSolutionResult();
            SubmissionRequest submissionRequest = new SubmissionRequest();
            SubmissionBatch submissionBatch = new SubmissionBatch(Arrays.asList(submissionRequest));
            List<SubmissionResult> submissionResult = codeExecutorService.createSubmissionBatch(submissionBatch);
            if(submissionResult!=null){

            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void evaluateSolution(ProblemSolutionInput problemSolution) {

    }

    @Override
    public Optional<ProblemSolution> getProblemSolution(String userId, Long problemId) {
        return problemSolutionRepository.findByUserIdAndProblemId(userId,problemId);
    }

    public Callable getCompileResult(String token){
            return new Callable() {
            @Override
            public Object call() throws Exception {
                SubmissionStatus submissionStatus = codeExecutorService.getSubmissionStatus(token);
                return submissionStatus;
            }
        };
    }

}
