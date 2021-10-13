package com.codework.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

@Data
public class SubmissionStatus {

    @JsonProperty("source_code")
    private String sourceCode;
    @JsonProperty("language_id")
    private String languageId;
    private String stdin;
    @JsonProperty("expected_output")
    private String expectedOutput;
    private String stdout;
    @JsonProperty("status_id")
    private String statusId;
    @JsonProperty("created_at")
    private Date createdAt;
    @JsonProperty("finished_at")
    private Date finishedAt;
    private String time;
    private String memory;
    private String stderr;
    private String token;
    @JsonProperty("cpu_time_limit")
    private String cpuTimeLimit;
    @JsonProperty("memory_limit")
    private String memoryLimit;
    @JsonProperty("stack_limit")
    private String stackLimit;
    @JsonProperty("compile_output")
    private String compileOutput;
    @JsonProperty("exit_code")
    private String exitCode;
    @JsonProperty("exit_signal")
    private String exitSignal;
    @JsonProperty("command_line_arguments")
    private String commandLineArgument;
    private String message;
    private Status status;

    @Data
    public static class Status {
        private Integer id;
        private String description;
    }

}



