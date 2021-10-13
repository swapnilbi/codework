package com.codework.service;

import com.codework.model.SubmissionResult;
import com.codework.model.SubmissionStatus;

import java.io.IOException;

public interface ICodeExecutorService {


    SubmissionResult createSubmission(String program, String stdin, Integer language) throws IOException;

    SubmissionStatus getSubmissionStatus(String token) throws IOException;


}
