package com.example.demorest.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.example.demorest.entity.Employee;
import com.example.demorest.repository.EmployeeDAO;

@Component
public class DataInit implements ApplicationRunner {

	private EmployeeDAO employeeDAO;

	@Autowired
	public DataInit(EmployeeDAO employeeDAO) {
		this.employeeDAO = employeeDAO;
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		long count = employeeDAO.count();

		if (count == 0) {
			Employee emp1 = new Employee();

			emp1.setFirstName("sunil");
			emp1.setLastName("varma");
			emp1.setDesignation("Software engineer");

			Employee emp2 = new Employee();

			emp2.setFirstName("ACF");
			emp2.setLastName("garu");
			emp2.setDesignation("Chairman");

			employeeDAO.save(emp1);
			employeeDAO.save(emp2);
		}
	}

}
