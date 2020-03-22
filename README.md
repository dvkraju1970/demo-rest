# demo-rest
swaggerConfig

package com.example.demorest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	@Bean
	public Docket productApi() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.example.demorest.resource"))
				.paths(PathSelectors.regex("/.*")).build();
	}
}

-----------------------------------------------------------------------------------------------------------------------
Employee.java entity

package com.example.demorest.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "EMP_DETAILS")
public class Employee {

	@Id
	@GeneratedValue
	@Column(name = "ID", nullable = false)
	private Long id;

	@Column(name = "FNAME")
	private String firstName;

	@Column(name = "LNAME")
	private String lastName;

	@Column(name = "DESIGNATION")
	private String designation;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

}

--------------------------------------------------------------------------------------------------------------------------

EmployeeDAO.java   

package com.example.demorest.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demorest.entity.Employee;

@Transactional
@Repository
public interface EmployeeDAO extends CrudRepository<Employee, Long> {

	//public List<Employee> findByFirstName(String name);

	@Modifying
	@Query("UPDATE Employee emp SET emp.firstName = :fname, emp.lastName = :lname, emp.designation = :designation WHERE emp.id =:id")
	int updateEmployee(@Param("fname") String fname, @Param("lname") String lname,
			@Param("designation") String designation, @Param("id") Long id);

}

--------------------------------------------------------------------------------------------------------------------------

EmployeeResource 

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

------------------------------------------------------------------------------------------------------------------------------
EmployeeService 

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

--------------------------------------------------------------------------------------------------------------------------------
EmployeeVO

package com.example.demorest.vo;

import java.io.Serializable;

import lombok.Data;

@Data
public class EmployeeVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String firstName;
	private String lastName;
	private String designation;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

}

----------------------------------------------------------------------------------------------------------------------------------------

DataInit  

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

			emp2.setFirstName("karthik");
			emp2.setLastName("varma");
			emp2.setDesignation("Chairman");

			employeeDAO.save(emp1);
			employeeDAO.save(emp2);
		}
	}

}

-------------------------------------------------------------------------------------------------------------------------------

application.properties

server.port = 8091
spring.datasource.url=jdbc:h2:mem:jpadb
#spring.datasource.url=jdbc:h2:tcp://localhost/~/test
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.h2.console.enabled=true

#jpa
spring.jpa.show-sql=true
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.H2Dialect
