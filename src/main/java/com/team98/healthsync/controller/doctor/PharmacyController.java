package com.team98.healthsync.controller.doctor;

import com.team98.healthsync.enums.OrderState;
import com.team98.healthsync.models.PharmacyOrder;
import com.team98.healthsync.models.Prescription;
import com.team98.healthsync.repository.PharmacyOrderRepository;
import com.team98.healthsync.repository.PrescriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.sql.Date;
import java.sql.Timestamp;

@Controller
public class PharmacyController {

    @Autowired
    private PharmacyOrderRepository pharmacyOrderRepository;

    @Autowired
    private PrescriptionRepository prescriptionRepository;

    @GetMapping("/doctor/pharmacy/{id}")
    public ModelAndView addNewPharmacyorder(@PathVariable("id")  long id){
        Prescription prescription=prescriptionRepository.findById(id).orElseThrow();
        PharmacyOrder pharmacyOrder=new PharmacyOrder();
        pharmacyOrder.setPrescription(prescription);
        pharmacyOrder.setState(OrderState.Received);

        Timestamp ts=new Timestamp(System.currentTimeMillis());
        Date dt=new Date(ts.getTime());

        pharmacyOrder.setDateTime(dt);
        pharmacyOrderRepository.save(pharmacyOrder);


        var patientAppointmentUrl = "redirect:/doctor/appointments/" + prescription.getAppointment().getId();
        return new ModelAndView(patientAppointmentUrl,"order",true);

    }


}
