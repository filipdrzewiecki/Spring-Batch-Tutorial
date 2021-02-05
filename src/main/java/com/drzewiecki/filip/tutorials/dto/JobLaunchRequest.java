package com.drzewiecki.filip.tutorials.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobLaunchRequest {
    private String jobName;
    private Map<String, String> param;
}
