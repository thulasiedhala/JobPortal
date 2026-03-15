package com.jobportal.repository;

import com.jobportal.entity.Application;
import com.jobportal.entity.Job;
import com.jobportal.entity.Student;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ApplicationRepository extends JpaRepository<Application, Long> {

    // find application by job and student
    Optional<Application> findByJobIdAndStudentId(Long jobId, Long studentId);

    // find all applications of a student
    List<Application> findByStudent(Student student);

    // find all applications for a job
    List<Application> findByJob(Job job);
}