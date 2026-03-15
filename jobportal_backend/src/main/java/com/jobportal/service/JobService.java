package com.jobportal.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jobportal.entity.Application;
import com.jobportal.entity.Employer;
import com.jobportal.entity.Job;
import com.jobportal.repository.ApplicationRepository;
import com.jobportal.repository.EmployerRepository;
import com.jobportal.repository.JobRepository;

@Service
public class JobService {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private EmployerRepository employerRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    private Employer resolveEmployer(Long employerIdOrUserId) {
        return employerRepository.findById(employerIdOrUserId)
                .or(() -> employerRepository.findByUser_Id(employerIdOrUserId))
                .orElseThrow(() -> new RuntimeException(
                        "Employer not found for id/userId: " + employerIdOrUserId));
    }

    // Create Job
    public Job createJob(Job job, Long employerId) {
        Employer employer = resolveEmployer(employerId);
        job.setEmployer(employer);
        return jobRepository.save(job);
    }

    // Get all jobs
    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }

    // Get jobs posted by employer
    public List<Job> getEmployerJobs(Long employerId) {
        Employer employer = resolveEmployer(employerId);
        return jobRepository.findByEmployer(employer);
    }

    // Get applicants for a job
    public List<Application> getApplicants(Long jobId) {
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        return applicationRepository.findByJob(job);
    }

    // Job analytics
    public Map<String, Long> getJobAnalytics(Long jobId) {
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        List<Application> applications = applicationRepository.findByJob(job);

        long total = applications.size();
        long accepted = applications.stream()
                .filter(a -> "ACCEPTED".equals(a.getStatus()))
                .count();

        long rejected = applications.stream()
                .filter(a -> "REJECTED".equals(a.getStatus()))
                .count();

        long pending = applications.stream()
                .filter(a -> "APPLIED".equals(a.getStatus()) || "PENDING".equals(a.getStatus()))
                .count();

        Map<String, Long> analytics = new HashMap<>();
        analytics.put("total", total);
        analytics.put("accepted", accepted);
        analytics.put("rejected", rejected);
        analytics.put("pending", pending);

        return analytics;
    }
}