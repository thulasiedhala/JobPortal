package com.jobportal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.jobportal.entity.Student;
import com.jobportal.service.StudentService;

@RestController
@RequestMapping("/api/students")
@CrossOrigin("*")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping("/upload-resume")
    public Student uploadResume(
            @RequestParam Long studentId,
            @RequestParam MultipartFile file) throws Exception {

        return studentService.uploadResume(studentId, file);
    }
}