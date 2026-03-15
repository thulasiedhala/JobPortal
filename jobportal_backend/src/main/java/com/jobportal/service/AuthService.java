package com.jobportal.service;

import com.jobportal.dto.LoginRequest;
import com.jobportal.dto.RegisterRequest;
import com.jobportal.entity.Employer;
import com.jobportal.entity.Student;
import com.jobportal.entity.User;
import com.jobportal.repository.EmployerRepository;
import com.jobportal.repository.StudentRepository;
import com.jobportal.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private EmployerRepository employerRepository;

    // REGISTER
    public User register(RegisterRequest request) {

        User user = new User();
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setRole(request.getRole());

        User savedUser = userRepository.save(user);

        if (savedUser.getRole().equals("STUDENT")) {

            Student student = new Student();
            student.setUser(savedUser);
            student.setUniversity("Not Provided");
            student.setDegree("Not Provided");
            student.setGraduationYear(0);
            studentRepository.save(student);

        } else if (savedUser.getRole().equals("EMPLOYER")) {

            Employer employer = new Employer();
            employer.setUser(savedUser);
            employerRepository.save(employer);

        }

        return savedUser;
    }

    // LOGIN
    public User login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail());

        if (user == null) {
            throw new RuntimeException("User not found");
        }

        if (!user.getPassword().equals(request.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        return user;
    }
}