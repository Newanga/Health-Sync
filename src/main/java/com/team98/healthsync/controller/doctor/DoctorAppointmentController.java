package com.team98.healthsync.controller.doctor;

import com.team98.healthsync.models.Appointment;
import com.team98.healthsync.models.Medication;
import com.team98.healthsync.repository.AppointmentRepository;
import com.team98.healthsync.service.IDocHelper;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Controller
public class DoctorAppointmentController {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private IDocHelper helper;

    @GetMapping("/doctor/appointments/today")
    @ResponseBody
    public List<Appointment> getAllAppointmentsToday(){
        DateTime todayAtMidnight = new DateTime().withTimeAtStartOfDay();
        Date dateToday=todayAtMidnight.toDate();
        Date dateTomorrow=todayAtMidnight.toDate();
        dateTomorrow.setTime(dateTomorrow.getTime()+86400000);
        return appointmentRepository.findAllByChannelId_DoctorIdAndChannelId_DateTimeBetween(helper.GetDoctorId(),dateToday,dateTomorrow);
    }

    @GetMapping("/doctor/appointments/{id}")
    public ModelAndView getTodayAppointment(@PathVariable("id") @Min(value = 0) @NotNull long id){
        Appointment appointment=appointmentRepository.findById(id).orElseThrow();
        ModelAndView mv=new ModelAndView();
        Medication medication=new Medication();
        medication.setAppointment(appointment);
        mv.addObject("medication",medication);
        mv.setViewName("thymeleaf/doctor/appointment/patient_appointment");
        return mv;
    }
}
