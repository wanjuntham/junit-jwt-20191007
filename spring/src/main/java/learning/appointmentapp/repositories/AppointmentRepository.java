package learning.appointmentapp.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import learning.appointmentapp.entities.Appointment;;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    public List<Appointment> findByEmployeeEmailContains(String email);

    public List<Appointment> findByEmployeeId(Long id);

    public List<Appointment> findByTimeslotBetween(LocalDateTime start, LocalDateTime end);
}