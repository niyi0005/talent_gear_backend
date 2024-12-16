package org.cst8319.niyitangajeanpierre.talentgearbackend.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.cst8319.niyitangajeanpierre.talentgearbackend.entity.JobApplicationEntity;

import org.cst8319.niyitangajeanpierre.talentgearbackend.service.JobApplicationService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
@RequiredArgsConstructor
@Slf4j
public class JobApplicationController {

    private final JobApplicationService jobApplicationService;


    @PostMapping
    public ResponseEntity<JobApplicationEntity> applyForJob(@RequestParam Long jobId) {

        JobApplicationEntity newApplication = jobApplicationService.applyForJob(jobId);
        return ResponseEntity.ok(newApplication);
    }

    @GetMapping("/{jobId}")
    public ResponseEntity<List<JobApplicationEntity>> getJobApplication(@PathVariable Long jobId) {
        List<JobApplicationEntity> jobs = jobApplicationService.getApplicationsByJobId(jobId);
        return ResponseEntity.ok(jobs);

    }
}
