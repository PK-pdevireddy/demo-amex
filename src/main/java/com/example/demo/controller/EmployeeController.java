package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Employee;
import com.example.demo.repository.EmployeeRepository;

@RestController
@RequestMapping("/api")
public class EmployeeController {

	@Autowired
	EmployeeRepository employeeRepository;

	@RequestMapping(value = "/employee", method = RequestMethod.GET)
	public ResponseEntity<List<Employee>> getAllEmployees() {
		List<Employee> allEmployees = employeeRepository.findAll();

		return new ResponseEntity(allEmployees, HttpStatus.OK);
	}

	@RequestMapping(value = "/employee", method = RequestMethod.POST)
	public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee) {
		employeeRepository.save(employee);
		return new ResponseEntity<Employee>(employee, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/employee", method = RequestMethod.PUT)
	public ResponseEntity<Void> updateEmployee(@RequestBody Employee employee) {
		Employee employee1 = employeeRepository.findById(employee.getId())
				.orElseThrow(() -> new ResourceNotFoundException("employee1", "id", employee.getId()));

		if (employee.getFirstname() != null)
			employee1.setFirstname(employee.getFirstname());
		if (employee.getLastname() != null)
			employee1.setLastname(employee.getLastname());
		if (employee.getDesignation() != null)
			employee1.setDesignation(employee.getDesignation());
		if (employee.getSalary() != null)
			employee1.setSalary(employee.getSalary());

		employeeRepository.save(employee1);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@RequestMapping(value = "/employee/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteEmployee(@PathVariable("id") Long id) {
		doDeleteEmployee(id);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	public void doDeleteEmployee(Long id) throws ResourceNotFoundException {
		Employee employee = employeeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("employee", "id", id));

		employeeRepository.delete(employee);
	}

}
