package org.cst8319.niyitangajeanpierre.talentgearbackend.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.cst8319.niyitangajeanpierre.talentgearbackend.entity.JobApplicationEntity;
import org.cst8319.niyitangajeanpierre.talentgearbackend.entity.JobEntity;
import org.cst8319.niyitangajeanpierre.talentgearbackend.entity.UserEntity;
import org.cst8319.niyitangajeanpierre.talentgearbackend.repository.JobApplicationRepository;
import org.cst8319.niyitangajeanpierre.talentgearbackend.repository.JobRepository;
import org.cst8319.niyitangajeanpierre.talentgearbackend.repository.UserRepository;
import org.cst8319.niyitangajeanpierre.talentgearbackend.util.UtilMethods;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class JobApplicationService {
    private final JobApplicationRepository jobApplicationRepository;
    private final JobRepository jobRepository;
    private final UserRepository userRepository;
    private final UtilMethods utilMethods;


    public JobApplicationEntity applyForJob(Long jobId) {
        // Get the logged-in user's username
        String username = utilMethods.getLoggedInUsername();

        // Get logged-in user's ID from the database
        UserEntity user = userRepository.findByUsername(username);
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        // Check if the user has the role of a job_seeker
        if (!utilMethods.hasJobSeekerRole(user)) {
            throw new RuntimeException("User is not a job seeker");
        }

        JobEntity job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        // Create a new application entity
        JobApplicationEntity jobApplication = new JobApplicationEntity();
        jobApplication.setJobId(job.getId());
        jobApplication.setApplicantId(user.getId());
        jobApplication.setResumeContent(user.getResume().getFileUrl());
        jobApplication.setApplicationDate(LocalDate.now());
        jobApplication.setStatus(JobApplicationEntity.ApplicationStatus.SUBMITTED);

        // Save the application to the database
        return jobApplicationRepository.save(jobApplication);
    }

    public List<JobApplicationEntity> getApplicationsByJobId(Long jobId) {
        // Get the logged-in user's username
        String loggedInUsername = utilMethods.getLoggedInUsername();

        // Get logged-in user's ID from the database
        UserEntity user = userRepository.findByUsername(loggedInUsername);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        // Check if the logged-in user has the employer role
        if (!utilMethods.hasEmployerRole(user)) {
            throw new RuntimeException("User does not have the employer role");
        }

        // Check if the job exists and belongs to the logged-in user
        JobEntity job = jobRepository.findByIdAndEmployerUsername(jobId, loggedInUsername)
                .orElseThrow(() -> new RuntimeException("Job not found or does not belong to the logged-in user"));

        return jobApplicationRepository.findByJobId(jobId);
    }


}
