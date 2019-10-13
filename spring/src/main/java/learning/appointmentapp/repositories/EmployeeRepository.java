package learning.appointmentapp.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import learning.appointmentapp.entities.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Employee findByEmail(String email);
    List<Employee> findByName(String name);
    List<Employee> findAllByOrderByName();
    List<Employee> findByEmailContains(String email);
}