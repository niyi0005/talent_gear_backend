package org.cst8319.niyitangajeanpierre.talentgearbackend.service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.extern.slf4j.Slf4j;
import org.cst8319.niyitangajeanpierre.talentgearbackend.Dto.JobDto;
import org.cst8319.niyitangajeanpierre.talentgearbackend.Dto.JobSearchResponseDto;
import org.cst8319.niyitangajeanpierre.talentgearbackend.entity.JobEntity;
import org.cst8319.niyitangajeanpierre.talentgearbackend.entity.UserEntity;
import org.cst8319.niyitangajeanpierre.talentgearbackend.repository.JobRepository;
import org.cst8319.niyitangajeanpierre.talentgearbackend.repository.UserRepository;

import org.cst8319.niyitangajeanpierre.talentgearbackend.util.UtilMethods;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class JobService {

    private final JobRepository jobRepository;

    private final UserRepository userRepository;
    private final UtilMethods utilMethods;

    public JobService(JobRepository jobRepository, UserRepository userRepository, UtilMethods utilMethods) {
        this.jobRepository = jobRepository;
        this.userRepository = userRepository;
        this.utilMethods = utilMethods;
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

    public List<JobSearchResponseDto> searchJobs(String industry, String location, Double minSalary, Double maxSalary) {
        log.debug("Searching jobs by industry {}, location {}, min salary {}, max salary {}", industry, location, minSalary, maxSalary);

        List<JobEntity> jobs;

        if (industry != null) {
            jobs = jobRepository.findByIndustryContainingIgnoreCase(industry);
            log.debug("Found {} jobs in {} industry", jobs.size(), industry);
        } else if (location != null) {
            // Accepts search by city or state. Eg: San Francisco or CA
            jobs = jobRepository.findByLocationContainingIgnoreCase(location);
            log.debug("Found {} jobs in {} location", jobs.size(), location);
        } else if (minSalary != null) {
            jobs = jobRepository.findBySalaryGreaterThanEqual(minSalary);
            log.debug("Found {} jobs with salary above {} ", jobs.size(), minSalary);
        } else if (maxSalary != null) {
            jobs = jobRepository.findBySalaryLessThanEqual(maxSalary);
            log.debug("Found {} jobs with salary below {} ", jobs.size(), maxSalary);
        } else {
            throw new RuntimeException("At least one parameter must be provided");
        }



       jobs = applyFilters(jobs, industry, location, minSalary, maxSalary);

        log.debug("Found {} jobs", jobs.size());

        // Convert JobEntity list to JobSearchResponseDto list
        return utilMethods.mapToDtoList(jobs);
    }

    // Filter results as params are applied
    private List<JobEntity> applyFilters(List<JobEntity> jobs, String industry, String location, Double minSalary, Double maxSalary) {
        Stream<JobEntity> filteredJobs = jobs.stream();
        if (industry != null) {
            filteredJobs = filteredJobs.filter(job -> job.getIndustry().toLowerCase().contains(industry.toLowerCase()));
        }
        if (location != null) {
            filteredJobs = filteredJobs.filter(job -> job.getLocation().toLowerCase().contains(location.toLowerCase()));
        }
        if (minSalary != null) {
            filteredJobs = filteredJobs.filter(job -> job.getSalary() >= minSalary);
        }
        if (maxSalary != null) {
            filteredJobs = filteredJobs.filter(job -> job.getSalary() <= maxSalary);
        }
        List<JobEntity> filteredJobList = filteredJobs.toList();
        log.debug("Jobs filtered: {}", filteredJobList);
        return filteredJobList;
    }

}
