package org.cst8319.niyitangajeanpierre.talentgearbackend.Services;

import java.util.List;
import java.util.Optional;

import org.cst8319.niyitangajeanpierre.talentgearbackend.Repositories.JobRepository;
import org.cst8319.niyitangajeanpierre.talentgearbackend.entity.Job;
import org.springframework.stereotype.Service;

@Service
public class JobService {



    private final JobRepository jobRepository;

    public JobService(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }

    public Job getJobById(Long id) {
        Optional<Job> job = jobRepository.findById(id);
        return job.orElse(null);
    }

    public Job createJob(Job job) {
        return jobRepository.save(job);
    }

    public void deleteJob(Long id) {
        jobRepository.deleteById(id);
    }

}
