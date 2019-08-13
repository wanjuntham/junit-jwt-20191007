package learning.appointmentapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import learning.appointmentapp.entities.Appointment;;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

}