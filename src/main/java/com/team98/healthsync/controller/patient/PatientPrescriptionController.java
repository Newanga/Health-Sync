package com.team98.healthsync.controller.patient;


import com.team98.healthsync.models.Prescription;
import com.team98.healthsync.repository.AppointmentRepository;
import com.team98.healthsync.repository.PrescriptionRepository;
import com.team98.healthsync.service.IPatHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Controller
public class PatientPrescriptionController {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private PrescriptionRepository prescriptionRepository;

    @Autowired
    private IPatHelper patientHelper;


    @GetMapping("/pat/prescriptions")
    @ResponseBody
    private Iterable<Prescription> getPatPrescriptions(){
        return prescriptionRepository.findAllByAppointment_Patient_Id(patientHelper.GetPatientId());
    }

    @GetMapping("/pat/prescriptions/{id}")
    public ModelAndView viewPatPrescription(@PathVariable("id") @NotNull @Min(value = 0) long id){
        ModelAndView mv=new ModelAndView();
        Prescription prescription=prescriptionRepository.findById(id).orElse(null);
        String fullName= String.format("Dr. %s %s",prescription.getAppointment().getChannel().getDoctor().getFirstName(), prescription.getAppointment().getChannel().getDoctor().getLastName());
        prescription.getAppointment().getChannel().getDoctor().setFullName(fullName);
        mv.addObject("prescription",prescription);
        mv.setViewName("thymeleaf/patient/prescription/view_prescription");
        return mv;
    }
}
