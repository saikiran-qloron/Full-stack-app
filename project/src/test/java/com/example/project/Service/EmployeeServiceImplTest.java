package com.example.project.Service;

import com.example.project.Entities.Employee;
import com.example.project.Exception.DuplicateResource;
import com.example.project.Exception.ResourceNotFoundException;
import com.example.project.Repository.EmployeeDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // if you dont want to write autocloseable = MockitoAnnot...
class EmployeeServiceImplTest {
    private EmployeeServiceImpl underTest;

    @Mock
    private EmployeeDAO employeeDAO;

    @BeforeEach
    void setUp() {
        underTest = new EmployeeServiceImpl(employeeDAO);
    }

    @Test
    void getAllCustomers() {
        underTest.getAllEmployees();

        verify(employeeDAO).getAllEmployees();
    }

    @Test
    void getCustomerById() {
        Long id = 1L;

        Employee expected = new Employee(id, "12455", "Alex", "Wayne", "alex@hmail.com", 90000.0);
        when(employeeDAO.getEmployeeById(id)).thenReturn(Optional.of(expected));

        Employee actual = underTest.getEmployeeById(id);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void willThrowErrorWhenCustomerOptional() {
        Long id = 10L;
        when(employeeDAO.getEmployeeById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> underTest.getEmployeeById(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Employee with "+ id +" not found");
    }

    @Test
    void addEmployee() {
        String email = "alex@gmail.com";

        when(employeeDAO.emailExists(email)).thenReturn(false);

//        doReturn(false).when(employeeDAO.emailExists(email));

        Employee request = new Employee("12455", "Alex", "Wayne", "alex@gmail.com", 90000.0);

        underTest.createEmployee(request);

        ArgumentCaptor<Employee> captor = ArgumentCaptor.forClass(Employee.class);

        verify(employeeDAO).registerEmployee(captor.capture());

        Employee capturedEmployee = captor.getValue();

        assertThat(capturedEmployee.getId()).isNull();
        assertThat(capturedEmployee.getFirstName()).isEqualTo(request.getFirstName());
        assertThat(capturedEmployee.getLastName()).isEqualTo(request.getLastName());
        assertThat(capturedEmployee.getEmail()).isEqualTo(request.getEmail());
        assertThat(capturedEmployee.getEmpId()).isEqualTo(request.getEmpId());
    }

    @Test
    void emailExistsError() {
        String email = "alex@gmail.com";

        when(employeeDAO.emailExists(email)).thenReturn(true);

        Employee request = new Employee("12455", "Alex", "Wayne", "alex@gmail.com", 90000.0);

        assertThatThrownBy(() -> underTest.createEmployee(request))
                .isInstanceOf(DuplicateResource.class)
                .hasMessage("Employee with " + email + " already exists");

        verify(employeeDAO, never()).registerEmployee(any());
    }

    @Test
    void deleteCustomerById() {
        Long id = 1L;

        when(employeeDAO.idExists(id)).thenReturn(true);

        underTest.deleteEmployee(id);

        verify(employeeDAO).deleteEmployee(id);
    }

    @Test
    void idExistsError(){
        Long id = 10L;
        when(employeeDAO.idExists(id)).thenReturn(false);

        assertThatThrownBy(() -> underTest.deleteEmployee(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Employee with "+ id +" not found");

        verify(employeeDAO, never()).deleteEmployee(id);
    }

    @Test
    void updateCustomer() {
        Long id = 1L;
        Employee c = new Employee("12455", "Alex", "Wayne", "alex@hmail.com", 90000.0);
        when(employeeDAO.getEmployeeById(id)).thenReturn(Optional.of(c));

        Employee request = new Employee("12455", "Bruce", "Wayne", "bruce@hmail.com", 100000.0);

        underTest.updateEmployee(id, request);

        ArgumentCaptor<Employee> captor = ArgumentCaptor.forClass(Employee.class);

        verify(employeeDAO).updateEmployee(captor.capture());

        Employee capturedCustomer = captor.getValue();

        assertThat(capturedCustomer.getEmpId()).isEqualTo(request.getEmpId());
//        assertThat(capturedCustomer.getEmail()).isEqualTo(request.email());
        assertThat(capturedCustomer.getLastName()).isEqualTo(request.getLastName());
    }

    @Test
    void updateCustomerName() {
        Long id = 1L;
        Employee c = new Employee("12455", "Alex", "Wayne", "alex@hmail.com", 90000.0);
        when(employeeDAO.getEmployeeById(id)).thenReturn(Optional.of(c));

        Employee request = new Employee(null, "Alexei", null, null, null);

        underTest.updateEmployee(id, request);

        ArgumentCaptor<Employee> captor = ArgumentCaptor.forClass(Employee.class);

        verify(employeeDAO).updateEmployee(captor.capture());

        Employee capturedEmployee = captor.getValue();

        assertThat(capturedEmployee.getFirstName()).isEqualTo(request.getFirstName());
    }

    @Test
    void updateCustomerEmail() {
        Long id = 1L;
        Employee c = new Employee("12455", "Alex", "Wayne", "alex@hmail.com", 90000.0);
        when(employeeDAO.getEmployeeById(id)).thenReturn(Optional.of(c));

        Employee request = new Employee(null, null, null, "bruce@gmail.com", null);

        underTest.updateEmployee(id, request);

        ArgumentCaptor<Employee> captor = ArgumentCaptor.forClass(Employee.class);

        verify(employeeDAO).updateEmployee(captor.capture());

        Employee capturedEmployee = captor.getValue();

        assertThat(capturedEmployee.getEmail()).isEqualTo(c.getEmail());
    }
}
