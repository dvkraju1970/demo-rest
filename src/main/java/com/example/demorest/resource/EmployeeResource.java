package com.example.demorest.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demorest.entity.Employee;
import com.example.demorest.service.EmployeeService;
import com.example.demorest.vo.EmployeeVo;

import io.swagger.annotations.ApiOperation;

@RestController // this gives response of each method in json
@RequestMapping("/data/")
public class EmployeeResource {

	@Autowired
	private EmployeeService employeeService;

	@ApiOperation(value = "To add new Employee")
	@PostMapping(path = "addemp", consumes = "application/json", produces = "application/json")
	public Boolean addEmp(@RequestBody EmployeeVo employee) {
		return employeeService.addEmp(employee);
	}

//	@PostMapping(path = "getemp", consumes = "application/json", produces = "application/json")
//	public Employee getEmp(@RequestBody EmployeeVo employee) {
//		return employeeDAO.findByFirstName(employee.getFirstName()).get(0);
//	}

	@ApiOperation(value = "To retrieve all Employee details")
	@GetMapping(path = "getallemp", produces = "application/json")
	public List<Employee> getEmp() {
		return employeeService.getEmp();
	}

	@ApiOperation(value = "To update an employee based on id")
	@PostMapping(path = "updateemp", consumes = "application/json", produces = "application/json")
	public Boolean updateEmp(@RequestBody EmployeeVo employee,
			@RequestHeader(name = "EmployeeID", required = true) Long id) {
		return employeeService.updateEmp(employee, id);
	}

	@ApiOperation(value = "To delete an employee based on id")
	@PostMapping(path = "deleteemp", produces = "application/json")
	public Boolean deleteEmp(@RequestHeader(name = "EmployeeID", required = true) Long id) {
		return employeeService.deleteEmp(id);
	}

}
