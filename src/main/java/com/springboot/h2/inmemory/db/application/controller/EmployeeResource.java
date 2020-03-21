package com.springboot.h2.inmemory.db.application.controller;

import com.springboot.h2.inmemory.db.application.domain.Employee;
import com.springboot.h2.inmemory.db.application.exception.RecordNotFoundException;
import com.springboot.h2.inmemory.db.application.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Rest controller for handling insertion, update or delete events for data domain type employee.
 *
 * @author Harsha Mandadi
 */
@RestController
@RequestMapping("/api")
public class EmployeeResource {

    private static final Logger LOG = LoggerFactory.getLogger(EmployeeResource.class);

    private final EmployeeService service;

    public EmployeeResource(EmployeeService service) {
        this.service = service;
    }

    /**
     * Lists all employee present.
     *
     * @return ResponseEntity<List < Employee>>
     * @throws RecordNotFoundException
     */
    @GetMapping("/employees")
    public ResponseEntity<List<Employee>> getAllEmployees() throws RecordNotFoundException {
        LOG.debug("getAllEmployees method started");
        List<Employee> list = service.getAllEmployees();
        if (list.size() <= 0) {
            throw new RecordNotFoundException("No employees found");
        }
        return new ResponseEntity<>(list, new HttpHeaders(), HttpStatus.OK);
    }

    /**
     * Filters record by given user id if the user Id is present in database.
     *
     * @param id The id of employee using which we can filter the record.
     * @return ResponseEntity<Employee>
     * @throws RecordNotFoundException
     */
    @GetMapping("/employees/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable("id") Long id) throws RecordNotFoundException {
        LOG.debug("getEmployeeById method started {}", id);
        Employee entity = null;
        try {
            entity = service.getEmployeeById(id);
        } catch (RecordNotFoundException recordNotFoundException) {
            throw recordNotFoundException;
        }
        return new ResponseEntity<>(entity, new HttpHeaders(), HttpStatus.OK);
    }

    /**
     * Creates or updates employee in database.
     * If an user is already present then it'll update the corresponding user details otherwise creates a new entry.
     *
     * @param employee the Employee object with necessary information to make or update an entry.
     * @return ResponseEntity<Employee>
     * @throws RecordNotFoundException
     */
    @PostMapping("/employees")
    public ResponseEntity<Employee> createOrUpdateEmployee(@Valid @RequestBody Employee employee) throws RecordNotFoundException {
        LOG.debug("createOrUpdateEmployee method started {}", employee);
        Employee updated = service.createOrUpdateEmployee(employee);
        return new ResponseEntity<>(updated, new HttpHeaders(), HttpStatus.CREATED);
    }

    /**
     * Deletes record of employee if id is present in database else throws an exception with a message.
     *
     * @param id The id of employee using which we can delete the record.
     * @return ResponseEntity<Void>
     * @throws RecordNotFoundException
     */
    @DeleteMapping("/employees/{id}")
    public ResponseEntity<Void> deleteEmployeeById(@PathVariable("id") Long id) throws RecordNotFoundException {
        LOG.debug("deleteEmployeeById method started {}", id);
        service.deleteEmployeeById(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-adtApp-alert", "employee deleted");
        headers.add("X-adtApp-params", id.toString());
        return ResponseEntity.ok().headers(headers).build();
    }
}
