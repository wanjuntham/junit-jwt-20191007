package learning.appointmentapp.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import learning.appointmentapp.entities.Employee;
import learning.appointmentapp.repositories.EmployeeRepository;
import learning.appointmentapp.security.JwtUtils;

/**
 * EmployeeController
 */

@RestController
public class EmployeeController {

    @Autowired
    EmployeeRepository repo;

    @GetMapping(value = "/employees")
    public List<Employee> allEmployees(
        @RequestParam(required = false) String email) {
        if (email != null){
            return repo.findByEmailContains(email);
        }

        return repo.findAll();
    }

    @PostMapping(value = "/employees")
    public Employee createEmployee(@RequestBody Employee employee) {
        repo.save(employee);
        return employee;
    }

    @GetMapping(value = "/employees/{id}")
    public Employee employeeById(@PathVariable Long id){
        return repo.findById(id).get();
    }

    @PostMapping(value = "/signup")
    public Map<String, String> signup(@RequestBody Employee employee) {
        repo.save(employee);
        String token = JwtUtils.generateJwt(employee);
        Map<String, String> body = new HashMap<>();
        body.put("token", token);

        return body;
    }


}