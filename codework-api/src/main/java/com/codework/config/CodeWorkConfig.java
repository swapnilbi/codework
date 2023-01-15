package com.codework.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ConcurrentTaskExecutor;

import java.util.concurrent.Executors;

@Configuration
public class CodeWorkConfig {

    @Bean("evaluationTaskExecutor")
    public TaskExecutor problemEvaluationTaskExecutor() {
        ConcurrentTaskExecutor executor = new ConcurrentTaskExecutor(
                Executors.newFixedThreadPool(3));
        return executor;
    }
   
}
