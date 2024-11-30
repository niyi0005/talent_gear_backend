package org.cst8319.niyitangajeanpierre.talentgearbackend.Controllers;

import java.util.List;

import org.cst8319.niyitangajeanpierre.talentgearbackend.Services.JobService;
import org.cst8319.niyitangajeanpierre.talentgearbackend.entity.Job;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/jobs")
public class JobController {

     private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }


    @GetMapping
    public List<Job> getAllJobs() {
       
        return jobService.getAllJobs();
    }

 
    @PostMapping
    public Job createJob(@RequestBody Job job) {
  
        return jobService.createJob(job);
    }

   
    @GetMapping("/{id}")
    public Job getJobById(@PathVariable Long id) {
      
        return jobService.getJobById(id);
    }

  
    @DeleteMapping("/{id}")
    public void deleteJob(@PathVariable Long id) {
        jobService.deleteJob(id);
    }



}
