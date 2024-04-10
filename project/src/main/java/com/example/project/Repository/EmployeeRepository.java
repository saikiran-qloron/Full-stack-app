package com.example.project.Repository;

import com.example.project.Entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    public boolean existsEmployeeByEmail(String email);
    public boolean existsEmployeeById(Long id);
}
