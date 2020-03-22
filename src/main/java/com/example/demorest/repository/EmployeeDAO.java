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
