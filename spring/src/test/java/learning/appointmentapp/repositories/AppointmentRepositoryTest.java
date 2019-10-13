package learning.appointmentapp.repositories;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.Test;

import learning.appointmentapp.entities.Appointment;
import learning.appointmentapp.entities.Employee;


public class AppointmentRepositoryTest extends BaseRepositoryTest{


    @Test
    public void testFindAllEmpty() {
        List<Appointment> results = appRepo.findAll();

        assertEquals(0, results.size());
    }

    @Test
    public void testFindAllSuccess() {
        seedAppointment(LocalDateTime.now());
        seedAppointment(LocalDateTime.now().plusHours(3));

        List<Appointment> results = appRepo.findAll();

        assertEquals(2, results.size());
    }

    @Test
    public void testFindByTimeslotBetweenSuccess() {
        // Given
        seedAppointment(LocalDateTime.now());
        Appointment target = seedAppointment(LocalDateTime.now().plusHours(3));
        seedAppointment(LocalDateTime.now().plusHours(6));

        LocalDateTime start = LocalDateTime.now().plusHours(2);
        LocalDateTime end = LocalDateTime.now().plusHours(5);

        // When
        List<Appointment> results = appRepo.findByTimeslotBetween(start, end);

        // Then
        assertEquals(1, results.size());
        assertEquals(target.getId(), results.get(0).getId());
    }

    @Test
    public void testFindByTimeslotBetweenFailure() {
        // Given
        seedAppointment(LocalDateTime.now());
        seedAppointment(LocalDateTime.now().plusHours(3));
        seedAppointment(LocalDateTime.now().plusHours(6));

        LocalDateTime start = LocalDateTime.now().plusHours(8);
        LocalDateTime end = LocalDateTime.now().plusHours(10);

        // When
        List<Appointment> results = appRepo.findByTimeslotBetween(start, end);

        // Then
        assertEquals(0, results.size());
    }

    @Test
    public void testFindByEmployeeEmailContainsSuccess() {
        // Given
        Employee x = seedEmployee("Ming Xiang", "mingxiangchan@gmail.com");
        seedAppointment(LocalDateTime.now());
        Appointment y = seedAppointment(LocalDateTime.now());
        y.setEmployee(x);
        appRepo.save(y);

        // When
        List<Appointment> results = appRepo.findByEmployeeEmailContains("mingxiangchan");

        // THen
        assertEquals(1, results.size());
    }

    

}