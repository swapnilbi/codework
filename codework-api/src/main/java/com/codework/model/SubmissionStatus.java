package com.codework.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
public class SubmissionStatus {

    @SerializedName("source_code")
    private String sourceCode;
    @SerializedName("language_id")
    private String languageId;
    private String stdin;
    @SerializedName("expected_output")
    private String expectedOutput;
    private String stdout;
    @SerializedName("status_id")
    private String statusId;
    @SerializedName("created_at")
    private Date createdAt;
    @SerializedName("finished_at")
    private Date finishedAt;
    private String time;
    private String memory;
    private String stderr;
    private String token;
    @SerializedName("cpu_time_limit")
    private Float cpuTimeLimit;
    @SerializedName("memory_limit")
    private Float memoryLimit;
    @SerializedName("stack_limit")
    private String stackLimit;
    @SerializedName("compile_output")
    private String compileOutput;
    @SerializedName("exit_code")
    private String exitCode;
    @SerializedName("exit_signal")
    private String exitSignal;
    @SerializedName("command_line_arguments")
    private String commandLineArgument;
    private String message;
    private Status status;

    @Data
    public static class Status {
        private Integer id;
        private String description;
    }

}



