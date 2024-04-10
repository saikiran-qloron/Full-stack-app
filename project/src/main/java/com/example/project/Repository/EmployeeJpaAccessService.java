package com.example.project.Repository;

import com.example.project.Entities.Employee;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class EmployeeJpaAccessService implements EmployeeDAO {

    private EmployeeRepository repository;

    public EmployeeJpaAccessService(EmployeeRepository repository) {
        this.repository = repository;
    }

    @Override
    public void registerEmployee(Employee emp) {
        repository.save(emp);
    }

    @Override
    public List<Employee> getAllEmployees() {
        return repository.findAll();
    }

    @Override
    public Optional<Employee> getEmployeeById(Long id) {
        return repository.findById(id);
    }

    @Override
    public void updateEmployee(Employee updatedEmployee) {
        repository.save(updatedEmployee);
    }

    @Override
    public void deleteEmployee(Long id) {
        repository.deleteById(id);
    }

    @Override
    public boolean emailExists(String email){
        return repository.existsEmployeeByEmail(email);
    }

    @Override
    public boolean idExists(Long custId){
        return repository.existsEmployeeById(custId);
    }
}
