package learning.appointmentapp.controllers;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import learning.appointmentapp.entities.Appointment;
import learning.appointmentapp.entities.Employee;
import learning.appointmentapp.repositories.AppointmentRepository;
import learning.appointmentapp.repositories.EmployeeRepository;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public abstract class BaseControllerTest {
    @LocalServerPort
    int port;

    @Autowired
    TestRestTemplate restTemplate;

    @Autowired
    AppointmentRepository appRepo;

    @Autowired
    EmployeeRepository empRepo;

    @Before
    public void cleanDBBefore() {
        appRepo.deleteAll();
        empRepo.deleteAll();
        
    }

    @After
    public void cleanDB() {
        appRepo.deleteAll();
        empRepo.deleteAll();
    }

    public Employee seedEmployee() {
        Employee employee = new Employee();
        employee.setName("Test");
        employee.setEmail("test@test.com");
        employee.setPassword("testtest");
        empRepo.save(employee);
        return employee;
    }
    
    public Appointment seedAppointment(LocalDateTime time) {
        Appointment appointment = new Appointment();
        appointment.setTimeslot(time);
        appRepo.save(appointment);
        return appointment;
    }

    public String seedSignUpEmployeeToken(Employee employee) throws Exception{
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Employee> request = new HttpEntity<Employee>(employee, headers);
        
        
        String url = new URL("http://localhost:" + port + "/signup").toString();

        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
        
        String responseBody = response.getBody();
        
        Map<String,String> newEmployeeToken = new ObjectMapper().readValue(responseBody, Map.class);

        return newEmployeeToken.get("token");
    }

    public String seedLoginEmployeeToken(Employee employee) throws Exception {

        //login to the existing user
        HttpHeaders loginHeaders = new HttpHeaders();
        HttpEntity<Employee> loginRequest = new HttpEntity<Employee>(employee, loginHeaders);

        String loginUrl = new URL("http://localhost:" + port + "/login").toString();
        ResponseEntity<String> loginResponse = restTemplate.postForEntity(loginUrl, loginRequest, String.class);
        
        String loginResponseToken = loginResponse.getHeaders().get("Authorization").get(0).toString().replace("Bearer ", "");
        return loginResponseToken;
    }
    
}