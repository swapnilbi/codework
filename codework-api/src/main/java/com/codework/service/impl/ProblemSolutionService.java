package com.codework.service.impl;

import com.codework.model.ProblemSolution;
import com.codework.model.ProblemSolutionResult;
import com.codework.model.SubmissionResult;
import com.codework.model.SubmissionStatus;
import com.codework.service.ICodeExecutorService;
import com.codework.service.IProblemSolutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.*;

@Service
public class ProblemSolutionService implements IProblemSolutionService {

    @Autowired
    private ICodeExecutorService codeExecutorService;

    @Override
    public ProblemSolutionResult compileSolution(ProblemSolution problemSolution) {
        try{
            SubmissionResult submissionResult = codeExecutorService.createSubmission(problemSolution.getSolution(),problemSolution.getStdIn(),62);
            ProblemSolutionResult problemSolutionResult = new ProblemSolutionResult();
            if(submissionResult!=null){
                ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
                Future future = executorService.schedule(getCompileResult(submissionResult.getToken()), 10, TimeUnit.SECONDS);
                SubmissionStatus submissionStatus = (SubmissionStatus) future.get();
                if(submissionStatus.getStatus().getId() == 3){
                    problemSolutionResult.setResult(true);
                    problemSolutionResult.setCompilationLog(submissionStatus.getCompileOutput());
                    problemSolutionResult.setCompilationStatus(true);
                }
                problemSolutionResult.setStandardOutput(submissionStatus.getStdout());
                return problemSolutionResult;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
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
