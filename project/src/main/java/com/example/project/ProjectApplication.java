package com.example.project;

import com.example.project.Entities.Employee;
import com.example.project.Service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProjectApplication implements CommandLineRunner {
	@Autowired
	EmployeeService employeeService;

	public static void main(String[] args){
		SpringApplication.run(ProjectApplication.class, args);
	}

	@Override
	public void run(String args[]) throws Exception {
		Employee employee = new Employee("123456", "Mark", "Ruffalo", "mark@gmail.com", 80000.0);
		employeeService.createEmployee(employee);
	}

}
