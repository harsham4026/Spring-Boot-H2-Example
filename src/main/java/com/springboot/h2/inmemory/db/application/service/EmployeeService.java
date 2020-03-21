package com.springboot.h2.inmemory.db.application.service;

import com.springboot.h2.inmemory.db.application.exception.RecordNotFoundException;
import com.springboot.h2.inmemory.db.application.domain.Employee;

import java.util.List;

/**
 * Interface defining a set of methods to handle different type of operations.
 *
 * @author Harsha Mandadi
 */
public interface EmployeeService {

    List<Employee> getAllEmployees();

    Employee getEmployeeById(Long id) throws RecordNotFoundException;

    Employee createOrUpdateEmployee(Employee employee) throws RecordNotFoundException;

    void deleteEmployeeById(Long id) throws RecordNotFoundException;
}
