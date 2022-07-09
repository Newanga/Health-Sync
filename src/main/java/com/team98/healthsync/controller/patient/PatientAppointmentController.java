package com.team98.healthsync.controller.patient;

import com.team98.healthsync.models.Appointment;
import com.team98.healthsync.repository.AppointmentRepository;
import com.team98.healthsync.service.IPatHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Timestamp;
import java.util.Date;

@Controller
public class PatientAppointmentController {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private IPatHelper patientHelper;


    @GetMapping("/patient/appointments")
    @ResponseBody
    public Iterable<Appointment> getAllAppointments(){
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        Date dt=new Date(ts.getTime());
        return appointmentRepository.findAllByPatient_IdAndChannel_DateTimeBefore(patientHelper.GetPatientId(),dt);
    }

    @GetMapping("/patient/appointmentsfuture")
    @ResponseBody
    public Iterable<Appointment> getAllChannels(){
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        Date dt=new Date(ts.getTime());
        return appointmentRepository.findAllByPatient_IdAndChannel_DateTimeAfter(patientHelper.GetPatientId(),dt);
    }





}
