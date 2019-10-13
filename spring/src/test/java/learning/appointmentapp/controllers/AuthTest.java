package learning.appointmentapp.controllers;

import static org.junit.Assert.assertNotNull;

import java.net.URL;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import learning.appointmentapp.entities.Employee;
import learning.appointmentapp.security.JwtUtils;

/**
 * AuthTest
 */
public class AuthTest extends BaseControllerTest{

    @Test
    public void signUpTest() throws Exception{
        Employee emp1 = seedEmployee();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Employee> request = new HttpEntity<Employee>(emp1, headers);
        
        // set up the route and url
        String url = new URL("http://localhost:" + port + "/signup").toString();

        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

        String responseBody = response.getBody();
        System.out.println();
        Map<String,String> newEmployeeToken = new ObjectMapper().readValue(responseBody, Map.class);

        assertNotNull(JwtUtils.parseJwt(newEmployeeToken.get("token")));
    }

    @Test
    public void loginTest() throws Exception {
        Employee emp1 = seedEmployee();
        seedSignUpEmployeeToken(emp1);

        //login to the existing user
        HttpHeaders loginHeaders = new HttpHeaders();
        HttpEntity<Employee> loginRequest = new HttpEntity<Employee>(emp1, loginHeaders);

        String loginUrl = new URL("http://localhost:" + port + "/login").toString();
        ResponseEntity<String> loginResponse = restTemplate.postForEntity(loginUrl, loginRequest, String.class);

        String loginResponseToken = loginResponse.getHeaders().get("Authorization").get(0).toString().replace("Bearer ", "");
        
        assertNotNull(loginResponseToken);
    }
}