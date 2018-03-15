package com.example.demo;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.demo.controller.EmployeeController;
import com.example.demo.model.Employee;
import com.example.demo.repository.EmployeeRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmployeeControllerTest {

	@Mock
	EmployeeRepository employeeRepository;

	@InjectMocks
	EmployeeController employeeController;

	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testGetEmployees() throws Exception {

		Employee mockEmployee = new Employee("John", "Doe", "dev", 5000);
		List<Employee> employees = new ArrayList<>();
		employees.add(mockEmployee);

		when(employeeRepository.findAll()).thenReturn(employees);
		ResponseEntity<List<Employee>> employeesRes = employeeController.getAllEmployees();
		List<Employee> body = employeesRes.getBody();
		assertEquals(1, body.size());
		assertEquals(mockEmployee, body.get(0));

	}

}