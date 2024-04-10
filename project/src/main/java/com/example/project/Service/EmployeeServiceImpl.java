package com.example.project.Service;

import com.example.project.Entities.Employee;
import com.example.project.Exception.DuplicateResource;
import com.example.project.Exception.ResourceNotFoundException;
import com.example.project.Exception.ResourceValidationException;
import com.example.project.Repository.EmployeeDAO;
import com.example.project.Repository.EmployeeJpaAccessService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService{

    private EmployeeDAO employeeDAO;

    public EmployeeServiceImpl(EmployeeDAO employeeDAO) {
        this.employeeDAO = employeeDAO;
    }

    @Override
    public String createEmployee(Employee emp) {
        if(employeeDAO.emailExists(emp.getEmail())){
            throw new DuplicateResource("Employee with %s already exists".formatted(emp.getEmail()));
        }
        employeeDAO.registerEmployee(emp);
        return "Employee " + emp.getEmail() + " has been saved to database";
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeDAO.getAllEmployees();
    }

    @Override
    public Employee getEmployeeById(Long id) {
        return employeeDAO.getEmployeeById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee with "+ id +" not found"));
    }

    @Override
    public String updateEmployee(Long id, Employee updatedEmployee) {
        Employee emp = employeeDAO.getEmployeeById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee with "+ id +" not found"));

        boolean changes = false;

        if(updatedEmployee.getFirstName() != null && updatedEmployee.getFirstName().length() != 0 &&
                !updatedEmployee.getFirstName().equals(emp.getFirstName())){
            emp.setFirstName(updatedEmployee.getFirstName());
            changes = true;
        }

        if(updatedEmployee.getLastName() != null &&
                updatedEmployee.getLastName() != updatedEmployee.getLastName()){
            emp.setLastName(updatedEmployee.getLastName());
            changes = true;
        }

        if(updatedEmployee.getEmail() != null && updatedEmployee.getEmail().equals(emp.getEmail())){
            throw new DuplicateResource("Enter a new email ID");
        }
        else if(updatedEmployee.getEmail() != null && updatedEmployee.getEmail().length() != 0){
            emp.setEmail(updatedEmployee.getEmail());
            changes = true;
        }

        if(updatedEmployee.getSalary() != null &&
                updatedEmployee.getSalary() != updatedEmployee.getSalary()){
            emp.setSalary(updatedEmployee.getSalary());
            changes = true;
        }

        if(!changes) throw new ResourceValidationException("No changes found");

        employeeDAO.updateEmployee(emp);

        return "Employee with "+ id +" successfully updated";
    }

    @Override
    public void deleteEmployee(Long id) {
        if(!employeeDAO.idExists(id)){
            throw new ResourceNotFoundException("Employee with "+ id +" not found");
        }
        employeeDAO.deleteEmployee(id);
    }
}
