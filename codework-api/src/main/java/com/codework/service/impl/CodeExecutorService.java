package com.codework.service.impl;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.codework.model.SubmissionBatch;
import com.codework.model.SubmissionBatchStatus;
import com.codework.model.SubmissionRequest;
import com.codework.model.SubmissionResult;
import com.codework.model.SubmissionStatus;
import com.codework.service.ICodeExecutorService;
import com.google.gson.Gson;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

@Service
public class CodeExecutorService implements ICodeExecutorService {


    @Value("${judge0.url}")
    private String judgeHostUrl;

    @Value("${judge0.host}")
    private String judgeHost;

    @Value("${judge0.apiKey}")
    private String judgeApiKey;
    
    private OkHttpClient getHttpClient() {
    	OkHttpClient client = new OkHttpClient();
        client.setConnectTimeout(30, TimeUnit.SECONDS); // connect timeout
        client.setReadTimeout(30, TimeUnit.SECONDS);
        return client;
    }
    
    @Override
    public SubmissionStatus evaluateSubmission(SubmissionRequest submissionRequest) throws IOException {
        OkHttpClient client = getHttpClient();
        client.setConnectTimeout(15, TimeUnit.SECONDS); // connect timeout
        client.setReadTimeout(15, TimeUnit.SECONDS);
        com.squareup.okhttp.MediaType mediaType = com.squareup.okhttp.MediaType.parse("application/json");
        System.out.println(new Gson().toJson(submissionRequest));
        RequestBody body = RequestBody.create(mediaType, new Gson().toJson(submissionRequest));
        Request request = new Request.Builder()
            .url(judgeHostUrl+ "submissions?base64_encoded=true&fields=*&wait=true")
            .post(body)
            .addHeader("content-type", "application/json")
            .addHeader("x-rapidapi-host", judgeHost)
            .addHeader("x-rapidapi-key", judgeApiKey)
            .build();
        Response response = client.newCall(request).execute();
        System.out.println(response.code());
        if(response.code() == HttpStatus.OK.value()){
            return new Gson().fromJson(response.body().string(),SubmissionStatus.class);
        }
        return null;
    }

    @Override
    public List<SubmissionResult> createSubmissionBatch(SubmissionBatch submissionBatch) throws IOException {
        OkHttpClient client = getHttpClient();
        com.squareup.okhttp.MediaType mediaType = com.squareup.okhttp.MediaType.parse("application/json");
        System.out.println(new Gson().toJson(submissionBatch));
        RequestBody body = RequestBody.create(mediaType, new Gson().toJson(submissionBatch));
        Request request = new Request.Builder()
                .url(judgeHostUrl+ "submissions/batch?base64_encoded=true&fields=*")
                .post(body)
                .addHeader("content-type", "application/json")
                .addHeader("x-rapidapi-host", judgeHost)
                .addHeader("x-rapidapi-key", judgeApiKey)
                .build();
        Response response = client.newCall(request).execute();
        System.out.println(response.code());
        if(response.code() == HttpStatus.CREATED.value()){
            SubmissionResult[] submissionResults = new Gson().fromJson(response.body().string(),SubmissionResult[].class);
            return Arrays.asList(submissionResults);
        }
        return null;
    }

    @Override
    public SubmissionStatus getSubmissionStatus(String token) throws IOException {
        OkHttpClient client = getHttpClient();
        Request request = new Request.Builder()
                .url(judgeHostUrl+ "submissions/"+token+"?base64_encoded=true&fields=*")
                .get()
                .addHeader("content-type", "application/json")
                .addHeader("x-rapidapi-host", judgeHost)
                .addHeader("x-rapidapi-key", judgeApiKey)
                .build();
        Response response = client.newCall(request).execute();
        System.out.println(response.code());
        if(response.code() == HttpStatus.OK.value()){
            return new Gson().fromJson(response.body().string(),SubmissionStatus.class);
        }
        return null;
    }

    @Override
    public SubmissionBatchStatus getSubmissionBatchStatus(List<String> token) throws IOException {
        OkHttpClient client = getHttpClient();
        String tokens = token.stream().collect(Collectors.joining(","));
        Request request = new Request.Builder()
                .url(judgeHostUrl+ "submissions/batch?tokens="+tokens+"?base64_encoded=true&fields=*")
                .get()
                .addHeader("content-type", "application/json")
                .addHeader("x-rapidapi-host", judgeHost)
                .addHeader("x-rapidapi-key", judgeApiKey)
                .build();
        Response response = client.newCall(request).execute();
        System.out.println(response.code());
        if(response.code() == HttpStatus.OK.value()){
            return new Gson().fromJson(response.body().string(),SubmissionBatchStatus.class);
        }
        return null;
    }

}
