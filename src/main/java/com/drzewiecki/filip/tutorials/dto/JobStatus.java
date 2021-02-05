package com.drzewiecki.filip.tutorials.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class JobStatus {

    private Long jobId;
    private String jobName;
    private String statusCode;
    private Map<String, Object> param;
    private LocalDate started;
    private LocalDate stopped;
    private LocalDate lastChange;
    private LocalDate created;
    private String exitStatusCode;
    private String exitDescription;
    private List<String> stepExecutions;
}

