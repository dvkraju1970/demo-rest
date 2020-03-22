package com.example.demorest.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demorest.entity.Employee;
import com.example.demorest.repository.EmployeeDAO;
import com.example.demorest.vo.EmployeeVo;

@Service
public class EmployeeService {

	@Autowired
	private EmployeeDAO employeeDAO;

	public Boolean addEmp(EmployeeVo employee) {
		Employee emp = new Employee();
		emp.setFirstName(employee.getFirstName());
		emp.setLastName(employee.getLastName());
		emp.setDesignation(employee.getDesignation());
		try {
			employeeDAO.save(emp);
			return true;
		} catch (Exception ex) {
			return false;
		}

	}

	public Boolean deleteEmp(Long id) {
		Optional<Employee> emp = employeeDAO.findById(id);
		if (emp.isPresent()) {
			employeeDAO.delete(emp.get());
			return true;
		}
		return false;
	}

	public Boolean updateEmp(EmployeeVo employee, Long id) {
		int count = employeeDAO.updateEmployee(employee.getFirstName(), employee.getLastName(),
				employee.getDesignation(), id);
		if (count > 0)
			return true;
		return false;
	}

	public List<Employee> getEmp() {
		Iterable<Employee> emps = employeeDAO.findAll();
		List<Employee> listEmp = new ArrayList<>();
		emps.forEach(a -> listEmp.add(a));
		return listEmp;
	}

}
