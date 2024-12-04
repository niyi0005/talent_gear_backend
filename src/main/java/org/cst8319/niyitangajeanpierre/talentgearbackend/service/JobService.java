package org.cst8319.niyitangajeanpierre.talentgearbackend.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.cst8319.niyitangajeanpierre.talentgearbackend.Dto.JobDto;
import org.cst8319.niyitangajeanpierre.talentgearbackend.entity.JobEntity;
import org.cst8319.niyitangajeanpierre.talentgearbackend.entity.UserEntity;
import org.cst8319.niyitangajeanpierre.talentgearbackend.repository.JobRepository;
import org.cst8319.niyitangajeanpierre.talentgearbackend.repository.UserRepository;

import org.springframework.stereotype.Service;

@Service
public class JobService {

    private final JobRepository jobRepository;

    private final UserRepository userRepository;

    public JobService(JobRepository jobRepository, UserRepository userRepository) {
        this.jobRepository = jobRepository;
        this.userRepository = userRepository;
    }

    /*
     * public List<JobEntity> getAllJobs() {
     * return jobRepository.findAll();
     * }
     */

    public List<JobDto> getAllJobs() {
        List<JobEntity> jobs = jobRepository.findAll();
        return jobs.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /*
     * public JobDto getJobById(Long id) {
     * Optional<JobEntity> job = jobRepository.findById(id);
     * if (job == null) {
     * throw new RuntimeException("Job not found for id: " + id);
     * } else {
     * return convertToDTO(job);
     * }
     * }
     */

     public JobEntity createJob(JobEntity jobEntity, Long employerId) {
        // Find the employer by ID
        UserEntity employer = userRepository.findById(employerId)
            .orElseThrow(() -> new RuntimeException("Employer not found"));
    
        // Set the employer to the job
        jobEntity.setEmployer(employer);
    
        // Save and return the job entity
        return jobRepository.save(jobEntity);
    }

    public void deleteJob(Long id) {
        jobRepository.deleteById(id);
    }

    private JobDto convertToDTO(JobEntity job) {
        JobDto jobDTO = new JobDto();
        jobDTO.setId(job.getId());
        jobDTO.setName(job.getName());
        jobDTO.setDescription(job.getDescription());
        jobDTO.setIndustry(job.getIndustry());
        jobDTO.setLocation(job.getLocation());
        jobDTO.setSalary(job.getSalary());
        jobDTO.setEmployerId(job.getEmployer().getId()); // Reference only the ID of the employer
        return jobDTO;
    }

    /* private JobEntity convertToEntity(JobDto jobDto, UserEntity employer) {
        JobEntity job = new JobEntity();
        job.setId(jobDto.getId()); 
        job.setName(jobDto.getName());
        job.setDescription(jobDto.getDescription());
        job.setIndustry(jobDto.getIndustry());
        job.setLocation(jobDto.getLocation());
        job.setSalary(jobDto.getSalary());
        job.setEmployer(employer); 
        return job;
    } */

}
