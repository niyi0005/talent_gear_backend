package org.cst8319.niyitangajeanpierre.talentgearbackend.controller;

import lombok.RequiredArgsConstructor;
import org.cst8319.niyitangajeanpierre.talentgearbackend.entity.JobApplicationEntity;
import org.cst8319.niyitangajeanpierre.talentgearbackend.service.JobApplicationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/applications")
@RequiredArgsConstructor
public class JobApplicationController {

    private final JobApplicationService jobApplicationService;

    @PostMapping
    public ResponseEntity<JobApplicationEntity> applyForJob(@RequestParam Long userId, @RequestParam Long jobId) {
        JobApplicationEntity newApplication = jobApplicationService.applyForJob(userId, jobId);
        return ResponseEntity.ok(newApplication);
    }
}
