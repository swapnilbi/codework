package com.codework.service.impl;

import com.codework.model.*;
import com.codework.service.ICodeExecutorService;
import com.google.gson.Gson;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CodeExecutorService implements ICodeExecutorService {


    @Value("${judge0.url}")
    private String judgeHostUrl;

    @Value("${judge0.host}")
    private String judgeHost;

    @Value("${judge0.apiKey}")
    private String judgeApiKey;

    private final static String GET_SUBMISSION_STATUS_FIELDS = "stdin,expected_output,stdout,status_id,time,memory,stderr,token,compile_output,exit_code,message,status";
    
    private OkHttpClient getHttpClient() {
    	OkHttpClient client = new OkHttpClient();
        client.setConnectTimeout(20, TimeUnit.SECONDS); // connect timeout
        client.setReadTimeout(60, TimeUnit.SECONDS);
        return client;
    }
    
    @Override
    public SubmissionStatus evaluateSubmission(SubmissionRequest submissionRequest) throws IOException {
        OkHttpClient client = getHttpClient();
        client.setConnectTimeout(15, TimeUnit.SECONDS); // connect timeout
        client.setReadTimeout(15, TimeUnit.SECONDS);
        com.squareup.okhttp.MediaType mediaType = com.squareup.okhttp.MediaType.parse("application/json");
        log.debug("evaluateSubmission "+ new Gson().toJson(submissionRequest));
        RequestBody body = RequestBody.create(mediaType, new Gson().toJson(submissionRequest));
        Request request = new Request.Builder()
            .url(judgeHostUrl+ "submissions?base64_encoded=true&wait=true&fields="+GET_SUBMISSION_STATUS_FIELDS)
            .post(body)
                .addHeader("content-type", "application/json; charset=utf-8")
            .addHeader("x-rapidapi-host", judgeHost)
            .addHeader("x-rapidapi-key", judgeApiKey)
            .build();
        Response response = client.newCall(request).execute();
        log.debug("responseCode "+response.code());
        if(response.code() == HttpStatus.OK.value()){
            return new Gson().fromJson(response.body().string(),SubmissionStatus.class);
        }
        return null;
    }

    @Override
    public List<SubmissionResult> createSubmissionBatch(SubmissionBatch submissionBatch) throws IOException {
        OkHttpClient client = getHttpClient();
        com.squareup.okhttp.MediaType mediaType = com.squareup.okhttp.MediaType.parse("application/json");
        log.debug("createSubmissionBatch="+ new Gson().toJson(submissionBatch));
        RequestBody body = RequestBody.create(mediaType, new Gson().toJson(submissionBatch));
        Request request = new Request.Builder()
                .url(judgeHostUrl+ "submissions/batch?base64_encoded=true&fields=*")
                .post(body)
                .addHeader("content-type", "application/json; charset=utf-8")
                .addHeader("x-rapidapi-host", judgeHost)
                .addHeader("x-rapidapi-key", judgeApiKey)
                .build();
        Response response = client.newCall(request).execute();
        log.debug("responseCode "+response.code());
        if(response.code() == HttpStatus.CREATED.value()){
            SubmissionResult[] submissionResults = new Gson().fromJson(response.body().string(),SubmissionResult[].class);
            return Arrays.asList(submissionResults);
        }
        return null;
    }

    @Override
    public SubmissionStatus getSubmissionStatus(String token) throws IOException {
        OkHttpClient client = getHttpClient();
        String url = judgeHostUrl+ "submissions/"+token+"?base64_encoded=true&fields="+GET_SUBMISSION_STATUS_FIELDS;
        log.debug("getSubmissionStatus "+url);
        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("content-type", "application/json; charset=utf-8")
                .addHeader("x-rapidapi-host", judgeHost)
                .addHeader("x-rapidapi-key", judgeApiKey)
                .build();
        Response response = client.newCall(request).execute();
        log.debug("responseCode "+response.code());
        if(response.code() == HttpStatus.OK.value()){
            return new Gson().fromJson(response.body().string(),SubmissionStatus.class);
        }
        return null;
    }

    @Override
    public SubmissionBatchStatus getSubmissionBatchStatus(List<String> token) throws IOException {
        OkHttpClient client = getHttpClient();
        String tokens = token.stream().collect(Collectors.joining(","));
        String url = judgeHostUrl+ "submissions/batch?tokens="+tokens+"&base64_encoded=true&fields="+GET_SUBMISSION_STATUS_FIELDS;
        log.debug("getSubmissionBatchStatus "+url);
        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("content-type", "application/json; charset=utf-8")
                .addHeader("x-rapidapi-host", judgeHost)
                .addHeader("x-rapidapi-key", judgeApiKey)
                .build();
        Response response = client.newCall(request).execute();
        log.debug("responseCode "+response.code());
        if(response.code() == HttpStatus.OK.value()){
            return new Gson().fromJson(response.body().string(),SubmissionBatchStatus.class);
        }
        return null;
    }

}
