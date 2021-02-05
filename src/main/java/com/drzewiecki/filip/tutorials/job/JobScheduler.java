package com.drzewiecki.filip.tutorials.job;

import com.drzewiecki.filip.tutorials.service.JobLaunchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@ConditionalOnProperty(value = "scheduling.update-users.enable", havingValue = "true", matchIfMissing = true)
public class JobScheduler extends AbstractJobScheduler{

    public JobScheduler(JobLaunchService jobLaunchService) {
        super(jobLaunchService);
    }

    @Scheduled(fixedDelayString = "${update-users.job.period}")
    public void runInvoiceJob() throws InterruptedException {
        runScheduledJob(JobConfig.UPDATE_USERS_JOB);
    }
}