package com.drzewiecki.filip.tutorials.component;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class LoggingJobListener implements JobExecutionListener {

    @Override
    public void beforeJob(JobExecution jobExecution) {
        // Empty implementation
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        // Empty implementation
    }
}

