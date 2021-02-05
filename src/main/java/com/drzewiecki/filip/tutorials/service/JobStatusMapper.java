package com.drzewiecki.filip.tutorials.service;

import com.drzewiecki.filip.tutorials.dto.JobStatus;
import lombok.Data;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Data
class JobStatusMapper {

    private JobStatusMapper() {}

    static JobStatus map(JobExecution jobExecution) {
        if (jobExecution == null) {
            return null;
        }

        JobStatus jobStatus = new JobStatus();
        jobStatus.setParam(new HashMap<>());
        for (String key : jobExecution.getJobParameters().getParameters().keySet()) {
            jobStatus.getParam().put(key, jobExecution.getJobParameters().getParameters().get(key).getValue());
        }
        jobStatus.setJobId(jobExecution.getId());
        jobStatus.setJobName(jobExecution.getJobInstance().getJobName());
        jobStatus.setStatusCode(jobExecution.getStatus().toString());
        jobStatus.setStarted(convertToLocalDate(jobExecution.getStartTime()));
        jobStatus.setStopped(convertToLocalDate(jobExecution.getEndTime()));
        jobStatus.setLastChange(convertToLocalDate(jobExecution.getLastUpdated()));
        jobStatus.setCreated(convertToLocalDate(jobExecution.getCreateTime()));
        jobStatus.setExitStatusCode(jobExecution.getExitStatus().getExitCode());
        jobStatus.setExitDescription(jobExecution.getExitStatus().getExitDescription());
        List<String> stepExecutions = new ArrayList<>();
        for (StepExecution stepExecution : jobExecution.getStepExecutions()) {
            stepExecutions.add(stepExecution.getSummary());
        }
        jobStatus.setStepExecutions(stepExecutions);

        return jobStatus;
    }

    private static LocalDate convertToLocalDate(Date toConvert) {
        if (toConvert != null) {
            return Instant.ofEpochMilli(toConvert.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
        }
        return null;
    }
}

