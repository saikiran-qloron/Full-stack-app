package com.example.project.Service;

import com.example.project.Entities.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {

    String createEmployee(Employee emp);

    List<Employee> getAllEmployees();

    Employee getEmployeeById(Long id);

    String updateEmployee(Long id, Employee updatedEmployee);

    void deleteEmployee(Long id);
}
