package learning.appointmentapp.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.net.URL;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;


import learning.appointmentapp.entities.Employee;


public class EmployeeControllerTest extends BaseControllerTest{
    
    @Test
    public void testAllEmployeesEmpty() throws Exception {
        // set up the route and url
        String url = new URL("http://localhost:" + port + "/employees").toString();

        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        assertEquals("[]", response.getBody());
    }

    @Test
    public void testAllEmployeesSuccess() throws Exception {
        seedEmployee();
        seedEmployee();
        seedEmployee();

        // set up the route and url
        String url = new URL("http://localhost:" + port + "/employees").toString();

        // Send the HTTP request
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        // converting the JSON string into Employee objects
        String responseBody = response.getBody();
        Employee[] createdEmployee = new ObjectMapper().readValue(responseBody, Employee[].class);

        assertEquals(3, createdEmployee.length);
    }

    @Test
    public void testCreateEmployee() throws Exception {
        // setup the data to be sent
        Employee employee = new Employee();
        employee.setName("John");
        employee.setEmail("john@gmail.com");
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Employee> request = new HttpEntity<>(employee, headers);

        // set up the route and url
        String url = new URL("http://localhost:" + port + "/employees").toString();

        // Send the HTTP request
        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

        // converting the JSON string into Employee objects
        String responseBody = response.getBody();

        Employee createdEmployee = new ObjectMapper().readValue(responseBody, Employee.class);

        assertNotEquals(null, createdEmployee.getId());
    }

    @Test
    public void testEmployeeParamSuccess() throws Exception{
        Employee emp1 = seedEmployee();

        // set up the route and url
        String url = new URL("http://localhost:" + port + "/employees?email=" + emp1.getEmail()).toString();

        // Send the HTTP request
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        // converting the JSON string into Employee objects
        String responseBody = response.getBody();
        Employee[] foundEmployee = new ObjectMapper().readValue(responseBody, Employee[].class);
    
        assertEquals(emp1.getEmail(), foundEmployee[0].getEmail());
    }

    @Test
    public void testEmployeeParamFail() throws Exception{
        // set up the route and url
        String url = new URL("http://localhost:" + port + "/employees?email=").toString();

        // Send the HTTP request
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        // converting the JSON string into Employee objects
        String responseBody = response.getBody();
        Employee[] foundEmployee = new ObjectMapper().readValue(responseBody, Employee[].class);

        assertEquals(0, foundEmployee.length);
    }


    
}