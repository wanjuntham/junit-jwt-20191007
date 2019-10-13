package learning.appointmentapp.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import learning.appointmentapp.entities.Appointment;
import learning.appointmentapp.entities.Employee;
import learning.appointmentapp.repositories.AppointmentRepository;
import learning.appointmentapp.repositories.EmployeeRepository;

@Service
public class BookingService {
    @Autowired
    AppointmentRepository appRepo;

    @Autowired
    EmployeeRepository empRepo;

    public List<LocalDateTime> checkAppointment(Employee employee) {
        List<Appointment> appointments = appRepo.findByEmployeeEmailContains(employee.getEmail());

        ArrayList<LocalDateTime> results = new ArrayList<>();

        for (Appointment app : appointments) {
            results.add(app.getTimeslot());
        }

        return results;
    }

    public Appointment bookAppointment(LocalDateTime timeslot, Employee employee) {
        if (ableToBook(timeslot)) {
            Appointment appointment = new Appointment();
            appointment.setTimeslot(timeslot);
            appointment.setEmployee(employee);
            appRepo.save(appointment);
            return appointment;
        }
        return null;
    }

    public boolean ableToBook(LocalDateTime timeslot) {
        // check if the appointment is within 2 hrs after a previous appointment
        LocalDateTime start = timeslot.minusHours(2);

        // check if there are any appointments within 2 hours after the timeslot
        LocalDateTime end = timeslot.plusHours(2);

        // make sure there are no existing appointments
        List<Appointment> existingAppointments = appRepo.findByTimeslotBetween(start, end);
        return existingAppointments.size() == 0;
    }

}