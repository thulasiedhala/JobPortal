package com.jobportal.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.jobportal.entity.Application;
import com.jobportal.entity.Job;
import com.jobportal.service.JobService;

@RestController
@RequestMapping("/api/jobs")
@CrossOrigin(origins = "*")
public class JobController {

    @Autowired
    private JobService jobService;

    // Create job
    @PostMapping("/create/{employerId}")
    public Job createJob(@RequestBody Job job, @PathVariable Long employerId) {
        return jobService.createJob(job, employerId);
    }

    // Get all jobs
    @GetMapping
    public List<Job> getAllJobs() {
        return jobService.getAllJobs();
    }

    // Get jobs by employer
    @GetMapping("/employer/{employerId}")
    public List<Job> getEmployerJobs(@PathVariable Long employerId) {
        return jobService.getEmployerJobs(employerId);
    }

    // Get applicants for a job
    @GetMapping("/{jobId}/applicants")
    public List<Application> getApplicants(@PathVariable Long jobId) {
        return jobService.getApplicants(jobId);
    }

    // Job analytics
    @GetMapping("/{jobId}/analytics")
    public Map<String, Long> getAnalytics(@PathVariable Long jobId) {
        return jobService.getJobAnalytics(jobId);
    }

}