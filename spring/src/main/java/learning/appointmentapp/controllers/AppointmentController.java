package learning.appointmentapp.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import learning.appointmentapp.entities.Appointment;
import learning.appointmentapp.entities.Employee;
import learning.appointmentapp.repositories.AppointmentRepository;

/**
 * AppointmentController
 */

@RestController
public class AppointmentController {

    @Autowired
    AppointmentRepository repo;


    @GetMapping(value = "/appointments")
    public List<Appointment> allAppointments() {
        return repo.findAll();
    }

    @GetMapping(value = "/appointments/{id}")
    public Appointment appointmentsById(@PathVariable Long id) {
        return repo.findById(id).get();
    }

    @GetMapping(value = "/employees/{id}/appointments")
    public List<Appointment> appointmentsByEmployee(@PathVariable Long id) {
        return repo.findByEmployeeId(id);
    }

    @PostMapping(value = "/appointments")
    public Appointment createAppointment(@RequestBody Appointment appointment,
    @RequestParam(required = false) Long employeeId) {
        if (employeeId != null) {
            
        }
        repo.save(appointment);
        return appointment;        
    }
}