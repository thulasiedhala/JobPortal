package com.jobportal.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.jobportal.entity.Employer;

public interface EmployerRepository extends JpaRepository<Employer, Long> {
    Optional<Employer> findByUser_Id(Long userId);
}