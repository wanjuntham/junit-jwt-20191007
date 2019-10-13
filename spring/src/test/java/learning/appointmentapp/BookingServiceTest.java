package learning.appointmentapp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import learning.appointmentapp.entities.Appointment;
import learning.appointmentapp.entities.Employee;
import learning.appointmentapp.repositories.AppointmentRepository;
import learning.appointmentapp.repositories.EmployeeRepository;
import learning.appointmentapp.services.BookingService;



@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class BookingServiceTest {
    @Autowired
    BookingService service;

    @Autowired
    AppointmentRepository appointmentRepo;

    @Autowired
    EmployeeRepository employeeRepo;

    @Test
    public void testCheckAppointment() {
        // Given
        // seed an employee
        // seed several appointments for the employee
        Employee x = seedEmployee("Ming Xiang", "Chan");
        seedAppointment(x);
        seedAppointment(x);
        seedAppointment(x);

        // When
        // trigger the checkAppointment function on BookingService
        List<LocalDateTime> results = service.checkAppointment(x);

        // Then
        // get a List<LocalDateTime> for the appointments for the employee
        assertEquals(3, results.size());
    }

    @Test
    public void testBookAppointmentSuccess() {
        // Given
        // seed an employee
        // seed several appointments for the employee
        Employee x = seedEmployee("Ming Xiang", "Chan");
        seedAppointment(x);
        LocalDateTime intendedTime = LocalDateTime.now().plusHours(3);

        // When
        // trigger the checkAppointment function on BookingService
        Appointment appointment = service.bookAppointment(intendedTime, x);

        // Then
        // get a List<LocalDateTime> for the appointments for the employee
        assertNotNull(appointment);
    }

    @Test
    public void testBookAppointmentFailureConflictBefore() {
        // Given
        Employee x = seedEmployee("Ming Xiang", "Chan");
        seedAppointment(x);
        LocalDateTime intendedTime = LocalDateTime.now().plusHours(1);

        // When
        Appointment appointment = service.bookAppointment(intendedTime, x);

        // Then
        assertEquals(null, appointment);
    }

    @Test
    public void testBookAppointmentFailureConflictAfter() {
        // Given
        Employee x = seedEmployee("Ming Xiang", "Chan");
        Appointment app = new Appointment();
        app.setTimeslot(LocalDateTime.now().plusHours(2));
        app.setEmployee(x);
        appointmentRepo.save(app);
        LocalDateTime intendedTime = LocalDateTime.now().plusHours(1);

        // When
        Appointment appointment = service.bookAppointment(intendedTime, x);

        // Then
        assertEquals(null, appointment);
    }

    public Employee seedEmployee(String name, String email) {
        Employee employee = new Employee();
        employee.setName(name);
        employee.setEmail(email);
        employeeRepo.save(employee);
        return employee;
    }

    public Appointment seedAppointment(Employee employee) {
        Appointment app = new Appointment();
        app.setTimeslot(LocalDateTime.now());
        app.setEmployee(employee);
        appointmentRepo.save(app);
        return app;
    }

}