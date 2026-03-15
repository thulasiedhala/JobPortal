package com.jobportal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.jobportal.entity.Application;
import com.jobportal.entity.Job;
import com.jobportal.service.EmployerService;

@RestController
@RequestMapping("/api/employer")
@CrossOrigin("*")
public class EmployerController {

    @Autowired
    private EmployerService employerService;

    // Employer can see jobs they posted
    @GetMapping("/jobs/{employerId}")
    public List<Job> getEmployerJobs(@PathVariable Long employerId) {
        return employerService.getEmployerJobs(employerId);
    }

    // Employer can see applicants for a job
    @GetMapping("/applicants/{jobId}")
    public List<Application> getApplicants(@PathVariable Long jobId) {
        return employerService.getApplicants(jobId);
    }
}