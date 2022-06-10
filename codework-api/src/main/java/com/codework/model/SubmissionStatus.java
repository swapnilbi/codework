package com.codework.model;

import com.codework.utility.ChallengeUtility;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.ToString;

@ToString
public class SubmissionStatus {

    private String stdin;
    @SerializedName("expected_output")
    private String expectedOutput;
    private String stdout;
    @SerializedName("status_id")
    private Integer statusId;
    private String time;
    private Long memory;
    private String stderr;
    private String token;
    @SerializedName("compile_output")
    private String compileOutput;
    @SerializedName("exit_code")
    private Integer exitCode;
    private String message;
    private Status status;

    @Data
    public static class Status {
        private Integer id;
        private String description;
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
		if(stdout!=null)
			return ChallengeUtility.decodeFromBase64(stdout);
		return null;
	}

	public void setStdout(String stdout) {
		this.stdout = stdout;
	}

	public Integer getStatusId() {
		return statusId;
	}

	public void setStatusId(Integer statusId) {
		this.statusId = statusId;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public Long getMemory() {
		return memory;
	}

	public void setMemory(Long memory) {
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

	public String getCompileOutput() {
		return ChallengeUtility.decodeFromBase64(compileOutput);
	}

	public void setCompileOutput(String compileOutput) {
		this.compileOutput = compileOutput;
	}

	public Integer getExitCode() {
		return exitCode;
	}

	public void setExitCode(Integer exitCode) {
		this.exitCode = exitCode;
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



