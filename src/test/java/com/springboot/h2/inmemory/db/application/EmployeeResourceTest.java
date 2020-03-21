package com.springboot.h2.inmemory.db.application;

import com.springboot.h2.inmemory.db.application.domain.Employee;
import com.springboot.h2.inmemory.db.application.exception.CustomExceptionHandler;
import com.springboot.h2.inmemory.db.application.service.EmployeeService;
import com.springboot.h2.inmemory.db.application.util.TestUtil;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.MockitoAnnotations;
import com.springboot.h2.inmemory.db.application.controller.EmployeeResource;
import com.springboot.h2.inmemory.db.application.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Test cases for testing the functionality.
 *
 * @author Harsha Mandadi
 */
@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SpringBootTest(classes = SpringApplication.class)
public class EmployeeResourceTest {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private CustomExceptionHandler exceptionTranslator;

    private Employee employee;

    private MockMvc restEmployeeMockMvc;

    private static String FIRST_NAME = "Herald";
    private static String LAST_NAME = "Johnson";
    private static String EMAIL = "herald.johnson@gmail.com";


    @Before
    public void setup() {
        MockitoAnnotations.initMocks(false);
        EmployeeResource employeeResource = new EmployeeResource(employeeService);
        this.restEmployeeMockMvc = MockMvcBuilders.standaloneSetup(employeeResource)
                .setControllerAdvice(exceptionTranslator)
                .setConversionService(TestUtil.createFormattingConversionService())
                .setMessageConverters(jacksonMessageConverter)
                .build();
    }

    @Before
    public void initTest() {
        this.employee = createEntity();
    }

    public static Employee createEntity() {
        Employee employee = new Employee(FIRST_NAME, LAST_NAME, EMAIL);
        employee.setId(0L);
        return employee;
    }

    @Test
    @Transactional
    public void testCreateEmployee() throws Exception {
        //before inserting find the size
        int databaseSizeBeforeCreate = employeeRepository.findAll().size();
        restEmployeeMockMvc
                .perform(MockMvcRequestBuilders.post("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(TestUtil.convertObjectToJsonBytes(employee)))
                .andExpect(MockMvcResultMatchers.status().isCreated());
        //after inserting fetch the records
        List<Employee> employeeList = employeeRepository.findAll();
        //compare records
        Assertions.assertThat(employeeList).hasSize(databaseSizeBeforeCreate + 1);
        Employee result = employeeList.get(employeeList.size() - 1);
        //compare the inserted record
        Assertions.assertThat(result.getFirstName()).isEqualTo(FIRST_NAME);
        Assertions.assertThat(result.getLastName()).isEqualTo(LAST_NAME);
        Assertions.assertThat(result.getEmail()).isEqualTo(EMAIL);
    }

    @Test
    @Transactional
    public void testGetEmployee() throws Exception {
        //save
        Employee emp = employeeRepository.saveAndFlush(employee);
        //fetch the inserted record
        restEmployeeMockMvc.perform(MockMvcRequestBuilders.get("/api/employees/{id}", emp.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(emp.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value(emp.getFirstName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value(emp.getLastName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(emp.getEmail()));
    }

    @Test
    @Transactional
    public void testDeleteEmployee() throws Exception {
        Employee emp = employeeRepository.saveAndFlush(employee);
        //Find the records size before deleting the record
        int recordsBeforeDelete = employeeRepository.findAll().size();
        restEmployeeMockMvc.perform(MockMvcRequestBuilders.delete("/api/employees/{id}", emp.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk());
        //Fetch the records after deleting the record
        List<Employee> employeeList = employeeRepository.findAll();
        Assertions.assertThat(employeeList.size()).isEqualTo(recordsBeforeDelete - 1);
    }
}
