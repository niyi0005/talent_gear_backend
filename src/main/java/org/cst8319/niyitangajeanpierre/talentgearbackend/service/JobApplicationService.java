package org.cst8319.niyitangajeanpierre.talentgearbackend.service;

import lombok.RequiredArgsConstructor;
import org.cst8319.niyitangajeanpierre.talentgearbackend.entity.JobApplicationEntity;
import org.cst8319.niyitangajeanpierre.talentgearbackend.entity.JobEntity;
import org.cst8319.niyitangajeanpierre.talentgearbackend.entity.UserEntity;
import org.cst8319.niyitangajeanpierre.talentgearbackend.repository.JobApplicationRepository;
import org.cst8319.niyitangajeanpierre.talentgearbackend.repository.JobRepository;
import org.cst8319.niyitangajeanpierre.talentgearbackend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JobApplicationService {
    private final JobApplicationRepository jobApplicationRepository;
    private final JobRepository jobRepository;
    private final UserRepository userRepository;


    public JobApplicationEntity applyForJob(Long userId, Long jobId) {
        // Find the user and job by their IDs
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        JobEntity job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found"));


        // Check if the user has the role of a job_seeker
        if (!hasJobSeekerRole(user)) {
            throw new RuntimeException("User is not a job seeker");
        }

        // Create a new application entity
        JobApplicationEntity jobApplication = new JobApplicationEntity();
        jobApplication.setJobId(job.getId());
        jobApplication.setApplicantId(user.getId());
        jobApplication.setApplicationDate(LocalDate.now());
        jobApplication.setStatus(JobApplicationEntity.ApplicationStatus.SUBMITTED);

        // Save the application to the database
        return jobApplicationRepository.save(jobApplication);
    }

    private boolean hasJobSeekerRole(UserEntity user) {
        return user.getRoles().stream()
                .anyMatch(role -> role.getName().equals("JOB_SEEKER"));
    }
}
