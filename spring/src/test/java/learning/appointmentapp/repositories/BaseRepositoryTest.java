package learning.appointmentapp.repositories;

import java.time.LocalDateTime;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import learning.appointmentapp.entities.Appointment;
import learning.appointmentapp.entities.Employee;

/**
 * BaseRepositoryTest
 */

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
abstract class BaseRepositoryTest {

    @Autowired
    AppointmentRepository appRepo;

    @Autowired
    EmployeeRepository empRepo;
    
    public Employee seedEmployee(String name, String email) {
        Employee employee = new Employee();
        employee.setName(name);
        employee.setEmail(email);
        empRepo.save(employee);
        return employee;
    }

    public Appointment seedAppointment(LocalDateTime time) {
        Appointment appointment = new Appointment();
        appointment.setTimeslot(time);
        appRepo.save(appointment);
        return appointment;
    }
}