package com.jobportal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jobportal.entity.Job;
import com.jobportal.entity.Employer;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {

    // Search jobs by title
    List<Job> findByTitleContainingIgnoreCase(String title);

    // Search jobs by location
    List<Job> findByLocationContainingIgnoreCase(String location);

    // Search jobs by job type (FULL_TIME, PART_TIME, INTERNSHIP)
    List<Job> findByJobType(String jobType);

    // Get all jobs posted by a specific employer
    List<Job> findByEmployer(Employer employer);

}