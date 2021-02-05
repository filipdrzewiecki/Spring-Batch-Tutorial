package com.drzewiecki.filip.tutorials.controller;

import com.drzewiecki.filip.tutorials.dto.JobLaunchRequest;
import com.drzewiecki.filip.tutorials.dto.JobStatus;
import com.drzewiecki.filip.tutorials.service.JobLaunchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/job")
public class JobLaunchController {

    private final JobLaunchService jobLaunchService;

    @GetMapping("/{id}")
    public ResponseEntity<JobStatus> getJobStatus(@PathVariable Long id) {
        return ResponseEntity.ok(jobLaunchService.getJobStatus(id));
    }

    @PostMapping
    public ResponseEntity<JobStatus> executeJob(@RequestBody JobLaunchRequest jobLaunchRequest) {
        return ResponseEntity.ok(jobLaunchService.run(jobLaunchRequest));
    }
}
