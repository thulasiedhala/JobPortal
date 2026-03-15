package com.jobportal.service;

import com.jobportal.entity.Application;
import com.jobportal.entity.Job;
import com.jobportal.entity.Student;
import com.jobportal.repository.ApplicationRepository;
import com.jobportal.repository.JobRepository;
import com.jobportal.repository.StudentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ApplicationService {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private StudentRepository studentRepository;

    public Application applyJob(Long jobId, Long userId, MultipartFile file) throws IOException {

        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        Student student = studentRepository.findByUser_Id(userId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        Application app = new Application();
        app.setJob(job);
        app.setStudent(student);
        app.setStatus("APPLIED");

        if (file != null && !file.isEmpty()) {

        	String uploadDir = System.getProperty("user.dir") + File.separator + "uploads";

        	File dir = new File(uploadDir);

        	if (!dir.exists()) {
        	    dir.mkdirs();
        	}

        	String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

        	File savedFile = new File(uploadDir + File.separator + fileName);

        	file.transferTo(savedFile);

        	// store only file name in database
        	app.setResumePath(fileName);
        }

        return applicationRepository.save(app);
    }
    public List<Application> getApplicationsByStudent(Long userId) {

        Student student = studentRepository.findByUser_Id(userId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        return applicationRepository.findByStudent(student);
    }

    public Application updateStatus(Long id, String status) {

        Application app = applicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Application not found"));

        app.setStatus(status);

        return applicationRepository.save(app);
    }
}