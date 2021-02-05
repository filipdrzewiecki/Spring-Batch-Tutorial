package com.drzewiecki.filip.tutorials.job;

import com.drzewiecki.filip.tutorials.dto.JobLaunchRequest;
import com.drzewiecki.filip.tutorials.dto.JobStatus;
import com.drzewiecki.filip.tutorials.service.JobLaunchService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;

@Slf4j
@AllArgsConstructor
public abstract class AbstractJobScheduler {

    private final JobLaunchService jobLaunchService;

    void runScheduledJob(String jobName) throws InterruptedException {
        JobLaunchRequest jobLaunchRequest = new JobLaunchRequest();
        jobLaunchRequest.setJobName(jobName);
        jobLaunchRequest.setParam(new HashMap<>());
        JobStatus jobExecution = jobLaunchService.run(jobLaunchRequest);
        log.info("{} started by scheduler", jobName);
        LocalDateTime started = LocalDateTime.now();
        Long jobId = jobExecution.getJobId();
        while (!"COMPLETED".equals(jobLaunchService.getJobStatus(jobId).getStatusCode()) &&
                !"FAILED".equals(jobLaunchService.getJobStatus(jobId).getStatusCode())) {
            Thread.sleep(Duration.ofMinutes(1).toMillis());
            if (Duration.between(started, LocalDateTime.now()).toHours() > 1) {
                break;
            }
        }
    }
}
