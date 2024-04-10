package com.example.project.Repository;

import com.example.project.Entities.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeDAO {
    void registerEmployee(Employee emp);

    List<Employee> getAllEmployees();

    Optional<Employee> getEmployeeById(Long id);

    void updateEmployee(Employee updatedEmployee);

    void deleteEmployee(Long id);

    boolean idExists(Long id);

    boolean emailExists(String email);
}
