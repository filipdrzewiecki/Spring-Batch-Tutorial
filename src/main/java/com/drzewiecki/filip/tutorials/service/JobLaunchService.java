package com.drzewiecki.filip.tutorials.service;

import com.drzewiecki.filip.tutorials.dto.JobLaunchRequest;
import com.drzewiecki.filip.tutorials.dto.JobStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersIncrementer;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class JobLaunchService {
    private final JobLauncher jobLauncher;
    private final JobExplorer jobExplorer;
    private final JobRegistry jobRegistry;
    public JobStatus run(JobLaunchRequest jobLaunchRequest) {
        log.info("Received jobLaunchRequest: " + jobLaunchRequest.getJobName());
        Job job;
        try {
            job = jobRegistry.getJob(jobLaunchRequest.getJobName());
        } catch (NoSuchJobException e) {
            throw new IllegalArgumentException("Job with name '" + jobLaunchRequest.getJobName() + "' was not found.");
        }
        JobParameters jobParameters = createJobParameters(jobLaunchRequest, job);
        JobExecution jobExecution = null;
        try {
            jobExecution = jobLauncher.run(job, jobParameters);
            log.info("Job " + jobExecution.getJobInstance().getJobName() + " with id " + jobExecution.getJobInstance().getInstanceId() + " started.");
        } catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException | JobParametersInvalidException e) {
            log.error("Error during job execution.", e);
        }
        return JobStatusMapper.map(jobExecution);
    }
    private JobParameters createJobParameters(JobLaunchRequest jobRequest, Job job) {
        JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
        if (jobRequest.getParam() != null) {
            for (String key : jobRequest.getParam().keySet()) {
                jobParametersBuilder.addString(key, jobRequest.getParam().get(key));
            }
        }
        jobParametersBuilder.addString("startTime", String.valueOf(System.currentTimeMillis()));
        JobParameters jobParameters = jobParametersBuilder.toJobParameters();
        JobParametersIncrementer jobParametersIncrementer = job.getJobParametersIncrementer();
        if (jobParametersIncrementer != null) {
            return jobParametersIncrementer.getNext(jobParameters);
        }
        return jobParameters;
    }
    public JobStatus getJobStatus(Long executionId) {
        JobExecution jobExecution = jobExplorer.getJobExecution(executionId);
        return JobStatusMapper.map(jobExecution);
    }
}
