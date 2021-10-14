package com.codework.model;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class SubmissionBatchStatus {

    private List<SubmissionStatus> submissions;

}



