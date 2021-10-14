package com.codework.service;

import com.codework.model.*;

import java.io.IOException;
import java.util.List;

public interface ICodeExecutorService {


    SubmissionStatus evaluateSubmission(SubmissionRequest submissionRequest) throws IOException;

    List<SubmissionResult> createSubmissionBatch(SubmissionBatch submissionBatch) throws IOException;

    SubmissionStatus getSubmissionStatus(String token) throws IOException;

    SubmissionBatchStatus getSubmissionBatchStatus(List<String> token) throws IOException;
}
