package com.drzewiecki.filip.tutorials.job;

import com.drzewiecki.filip.tutorials.component.LoggingJobListener;
import com.drzewiecki.filip.tutorials.service.TimestampIncrementer;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class JobConfig {

    public static final String UPDATE_USERS_JOB = "updateUsersJob";
    private static final String UPDATE_USERS_STEP = "updateUsersStep";

    @Bean
    Job updateUsersJob(JobBuilderFactory jobBuilderFactory, Map<String, Step> steps, LoggingJobListener loggingJobListener) {
        return jobBuilderFactory.get(UPDATE_USERS_JOB)
                .incrementer(new TimestampIncrementer())
                .start(steps.get(UPDATE_USERS_STEP))
                .listener(loggingJobListener)
                .build();
    }

    @Bean
    Step updateUsersStep(StepBuilderFactory stepBuilderFactory, UpdateUsersTasklet updateUsersTasklet) {
        return stepBuilderFactory.get(UPDATE_USERS_STEP)
                .tasklet(updateUsersTasklet)
                .build();
    }
}
