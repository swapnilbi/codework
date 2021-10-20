package com.codework.model;

import com.codework.utility.ChallengeUtility;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.ToString;

import java.util.Base64;
import java.util.Date;

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

	public String getSourceCode() {
		return sourceCode;
	}

	public void setSourceCode(String sourceCode) {
		this.sourceCode = sourceCode;
	}

	public String getLanguageId() {
		return languageId;
	}

	public void setLanguageId(String languageId) {
		this.languageId = languageId;
	}

	public String getStdin() {
		return ChallengeUtility.decodeFromBase64(stdin);
	}

	public void setStdin(String stdin) {
		this.stdin = stdin;
	}

	public String getExpectedOutput() {
		return expectedOutput;
	}

	public void setExpectedOutput(String expectedOutput) {
		this.expectedOutput = expectedOutput;
	}

	public String getStdout() {
		return ChallengeUtility.decodeFromBase64(stdout);
	}

	public void setStdout(String stdout) {
		this.stdout = stdout;
	}

	public String getStatusId() {
		return statusId;
	}

	public void setStatusId(String statusId) {
		this.statusId = statusId;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getFinishedAt() {
		return finishedAt;
	}

	public void setFinishedAt(Date finishedAt) {
		this.finishedAt = finishedAt;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getMemory() {
		return memory;
	}

	public void setMemory(String memory) {
		this.memory = memory;
	}

	public String getStderr() {
		return ChallengeUtility.decodeFromBase64(stderr);
	}

	public void setStderr(String stderr) {
		this.stderr = stderr;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Float getCpuTimeLimit() {
		return cpuTimeLimit;
	}

	public void setCpuTimeLimit(Float cpuTimeLimit) {
		this.cpuTimeLimit = cpuTimeLimit;
	}

	public Float getMemoryLimit() {
		return memoryLimit;
	}

	public void setMemoryLimit(Float memoryLimit) {
		this.memoryLimit = memoryLimit;
	}

	public String getStackLimit() {
		return stackLimit;
	}

	public void setStackLimit(String stackLimit) {
		this.stackLimit = stackLimit;
	}

	public String getCompileOutput() {
		return ChallengeUtility.decodeFromBase64(compileOutput);
	}

	public void setCompileOutput(String compileOutput) {
		this.compileOutput = compileOutput;
	}

	public String getExitCode() {
		return exitCode;
	}

	public void setExitCode(String exitCode) {
		this.exitCode = exitCode;
	}

	public String getExitSignal() {
		return exitSignal;
	}

	public void setExitSignal(String exitSignal) {
		this.exitSignal = exitSignal;
	}

	public String getCommandLineArgument() {
		return commandLineArgument;
	}

	public void setCommandLineArgument(String commandLineArgument) {
		this.commandLineArgument = commandLineArgument;
	}

	public String getMessage() {
		return ChallengeUtility.decodeFromBase64(message);
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
    
    
    

}



