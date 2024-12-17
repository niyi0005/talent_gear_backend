package org.cst8319.niyitangajeanpierre.talentgearbackend.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cst8319.niyitangajeanpierre.talentgearbackend.Dto.JobDto;
import org.cst8319.niyitangajeanpierre.talentgearbackend.Dto.JobSearchResponseDto;
import org.cst8319.niyitangajeanpierre.talentgearbackend.entity.JobEntity;
import org.cst8319.niyitangajeanpierre.talentgearbackend.service.JobService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("http://localhost:5173")
@RestController
@RequestMapping("/api/jobs")
public class JobController {

    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @GetMapping
    public List<JobDto> getAllJobs() {

        return jobService.getAllJobs();
    }

    @PostMapping
    public JobEntity createJob(@RequestBody JobEntity jobEntity) {
        Long employerId = jobEntity.getEmployer() != null ? jobEntity.getEmployer().getId() : null;
        return jobService.createJob(jobEntity, employerId); 
    }

    /*
     * @GetMapping("/{id}")
     * public JobDto getJobById(@PathVariable Long id) {
     * 
     * return jobService.getJobById(id);
     * }
     */

    @DeleteMapping("/{id}")
    public void deleteJob(@PathVariable Long id) {
        jobService.deleteJob(id);
    }

    @RequestMapping(value = "/**", method = RequestMethod.OPTIONS)
    public ResponseEntity<Void> handleOptions() {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<JobSearchResponseDto>> searchJobs(
                                   @RequestParam(required = false) String industry,
                                   @RequestParam(required = false) String location,
                                   @RequestParam(required = false) Double minSalary,
                                   @RequestParam(required = false) Double maxSalary) {
        try {
            List<JobSearchResponseDto> jobs = jobService.searchJobs(industry, location, minSalary, maxSalary);
            return ResponseEntity.ok(jobs);
        }catch (RuntimeException e){
            return ResponseEntity.badRequest().build();
        }
    }

}
