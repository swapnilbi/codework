package com.codework.service.impl;

import com.codework.model.SubmissionRequest;
import com.codework.model.SubmissionResult;
import com.codework.model.SubmissionStatus;
import com.codework.service.ICodeExecutorService;
import com.google.gson.Gson;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Service
public class CodeExecutorService implements ICodeExecutorService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${judge0.url}")
    private String judgeHostUrl;

    @Value("${judge0.host}")
    private String judgeHost;

    @Value("${judge0.apiKey}")
    private String judgeApiKey;

    @Override
    public SubmissionResult createSubmission(String program, String stdin, Integer languageId) throws IOException {
        OkHttpClient client = new OkHttpClient();
        SubmissionRequest submissionRequest = new SubmissionRequest();
        submissionRequest.setLanguageId(languageId);
        submissionRequest.setSourceCode(program);
        submissionRequest.setStdin(stdin);
        com.squareup.okhttp.MediaType mediaType = com.squareup.okhttp.MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, new Gson().toJson(submissionRequest));
        Request request = new Request.Builder()
            .url(judgeHostUrl+ "submissions?base64_encoded=true&fields=*")
            .post(body)
            .addHeader("content-type", "application/json")
            .addHeader("x-rapidapi-host", judgeHost)
            .addHeader("x-rapidapi-key", judgeApiKey)
            .build();
        Response response = client.newCall(request).execute();
        System.out.println(response.code()+ " "+response.body().toString());
        if(response.code() == HttpStatus.CREATED.value()){
            return new Gson().fromJson(response.body().string(),SubmissionResult.class);
        }
        return null;
    }

    @Override
    public SubmissionStatus getSubmissionStatus(String token) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(judgeHostUrl+ "submissions/"+token+"?base64_encoded=true&fields=*")
                .get()
                .addHeader("content-type", "application/json")
                .addHeader("x-rapidapi-host", judgeHost)
                .addHeader("x-rapidapi-key", judgeApiKey)
                .build();
        Response response = client.newCall(request).execute();
        System.out.println(response.code()+ " "+response.body().toString());
        if(response.code() == HttpStatus.OK.value()){
            return new Gson().fromJson(response.body().string(),SubmissionStatus.class);
        }
        return null;
    }

}
