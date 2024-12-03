package org.cst8319.niyitangajeanpierre.talentgearbackend.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.cst8319.niyitangajeanpierre.talentgearbackend.Dto.JobDto;
import org.cst8319.niyitangajeanpierre.talentgearbackend.entity.JobEntity;
import org.cst8319.niyitangajeanpierre.talentgearbackend.repository.JobRepository;
import org.springframework.boot.autoconfigure.batch.BatchProperties.Job;
import org.springframework.stereotype.Service;

@Service
public class JobService {

    private final JobRepository jobRepository;

    public JobService(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
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

  /*   public JobDto getJobById(Long id) {
        Optional<JobEntity> job = jobRepository.findById(id);
        if (job == null) {
            throw new RuntimeException("Job not found for id: " + id);
        } else {
            return convertToDTO(job);
        }
    } */

    public JobEntity createJob(JobEntity job) {
        return jobRepository.save(job);
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

}
