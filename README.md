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


