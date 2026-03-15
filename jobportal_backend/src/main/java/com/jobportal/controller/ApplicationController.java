package com.jobportal.controller;

import com.jobportal.entity.Application;
import com.jobportal.service.ApplicationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/applications")
public class ApplicationController {

    @Autowired
    private ApplicationService applicationService;


    @PostMapping("/apply")
    public Application applyJob(
            @RequestParam Long jobId,
            @RequestParam Long studentId,
            @RequestParam("file") MultipartFile file) throws IOException {

        return applicationService.applyJob(jobId, studentId, file);
    }

    // GET STUDENT APPLICATIONS
    @GetMapping("/student/{userId}")
    public List<Application> getStudentApplications(@PathVariable Long userId) {
        return applicationService.getApplicationsByStudent(userId);
    }

    // UPDATE STATUS (ACCEPT / REJECT)
    @PutMapping("/{id}")
    public Application updateStatus(@PathVariable Long id, @RequestParam String status) {
        return applicationService.updateStatus(id, status);
    }

    // DOWNLOAD RESUME
    @GetMapping("/resume/{fileName}")
    public ResponseEntity<Resource> viewResume(@PathVariable String fileName) {

        try {

            Path filePath = Paths.get("uploads").resolve(fileName).normalize();

            Resource resource = new UrlResource(filePath.toUri());

            if (!resource.exists() || !resource.isReadable()) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "inline; filename=\"" + resource.getFilename() + "\"")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(resource);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
    }