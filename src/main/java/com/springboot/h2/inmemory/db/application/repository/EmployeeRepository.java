package com.springboot.h2.inmemory.db.application.repository;

import com.springboot.h2.inmemory.db.application.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for handling data for table employee.
 *
 * @author Harsha Mandadi
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
