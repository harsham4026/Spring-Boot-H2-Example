package com.springboot.h2.inmemory.db.application.service.impl;

import com.springboot.h2.inmemory.db.application.exception.RecordNotFoundException;
import com.springboot.h2.inmemory.db.application.domain.Employee;
import com.springboot.h2.inmemory.db.application.repository.EmployeeRepository;
import com.springboot.h2.inmemory.db.application.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.nonNull;

/**
 * Implements functionality defined in EmployeeService which are add or update a employee, delete employee, get all employees,
 * get employee by id.
 *
 * @author Harsha Mandadi
 */
@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger LOG = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public List<Employee> getAllEmployees() {
        LOG.debug("getAllEmployees method started");
        List<Employee> employeeList = employeeRepository.findAll();
        if (employeeList.size() > 0)
            return employeeList;
        else
            return new ArrayList<>();
    }

    @Override
    public Employee getEmployeeById(Long id) throws RecordNotFoundException {
        LOG.debug("getEmployeeById method started {}", id);
        Employee employee = employeeRepository.findById(id).orElse(null);
        if (nonNull(employee))
            return employee;
        else
            throw new RecordNotFoundException("No employee record exist for given id : " + id);
    }

    @Override
    public Employee createOrUpdateEmployee(Employee employee) {
        LOG.debug("createOrUpdateEmployee method started {}", employee);
        Employee employeeRecord = employeeRepository.findById(employee.getId()).orElse(null);
        if (nonNull(employeeRecord)) {
            LOG.debug("update the record");
            employeeRecord.setEmail(employee.getEmail());
            employeeRecord.setFirstName(employee.getFirstName());
            employeeRecord.setLastName(employee.getLastName());
            return employeeRepository.save(employeeRecord);
        } else {
            LOG.debug("save the record");
            return employeeRepository.save(employee);
        }
    }

    @Override
    public void deleteEmployeeById(Long id) throws RecordNotFoundException {
        LOG.debug("deleteEmployeeById method started {}", id);
        Employee employee = employeeRepository.findById(id).orElse(null);
        if (nonNull(employee))
            employeeRepository.deleteById(id);
        else
            throw new RecordNotFoundException("No employee record exist for given id : " + id);
    }
}
