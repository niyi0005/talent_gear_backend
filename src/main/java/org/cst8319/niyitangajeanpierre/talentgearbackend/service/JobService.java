package org.cst8319.niyitangajeanpierre.talentgearbackend.service;

import java.util.List;
import java.util.Optional;

import org.cst8319.niyitangajeanpierre.talentgearbackend.entity.JobEntity;
import org.cst8319.niyitangajeanpierre.talentgearbackend.repository.JobRepository;
import org.springframework.stereotype.Service;

@Service
public class JobService {

    private final JobRepository jobRepository;

    public JobService(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    public List<JobEntity> getAllJobs() {
        return jobRepository.findAll();
    }

    public JobEntity getJobById(Long id) {
        Optional<JobEntity> jobEntity = jobRepository.findById(id);
        return jobEntity.orElse(null);
    }

    public JobEntity createJob(JobEntity job) {
        return jobRepository.save(job);
    }

    public void deleteJob(Long id) {
        jobRepository.deleteById(id);
    }

}
