package learning.appointmentapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import learning.appointmentapp.entities.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}