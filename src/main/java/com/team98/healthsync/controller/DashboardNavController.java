package com.team98.healthsync.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardNavController {


//    Admin Navigation



    @GetMapping("/admin/doctor")
    public String viewDoctors(){
        return "thymeleaf/admin/doctor/doctors";
    }

    @GetMapping("/admin/channel")
    public String viewChannels(){
        return "thymeleaf/admin/channel/channels";
    }

    @GetMapping("/admin/specialization")
    public String viewSpecializations(){
        return "thymeleaf/admin/specialization/specializations";
    }

    @GetMapping("/admin/patient")
    public String viewPatients(){
        return "thymeleaf/admin/patient/patients";
    }


    @GetMapping("/admin/appointment")
    public String viewAppointments(){
        return "thymeleaf/admin/appointment/appointments";
    }

    @GetMapping("/admin/labtechnician")
    public String viewLabTechnicianss(){
        return "thymeleaf/admin/labtechnician/labtechnicians";
    }


    @GetMapping("/admin/receptionist")
    public String viewReceptionists(){
        return "thymeleaf/admin/receptionist/receptionists";
    }

    @GetMapping("/admin/pharmacist")
    public String viewPharmacist(){
        return "thymeleaf/admin/pharmacist/pharmacists";
    }

    @GetMapping("/admin/labtestcategory")
    public String viewLabTestcategory(){ return "thymeleaf/admin/labtestcategory/categories"; }

    @GetMapping("/admin/medication")
    public String viewMedciation(){ return "thymeleaf/admin/medication/medications"; }

    @GetMapping("/admin/prescription")
    public String viewPrescription(){ return "thymeleaf/admin/prescription/prescriptions"; }

    @GetMapping("/admin/doctorreport")
    public String viewDoctorReport(){
        return "thymeleaf/admin/report/doctors";
    }

    @GetMapping("/admin/patientreport")
    public String viewPatinetReport(){
        return "thymeleaf/admin/report/patients";
    }

    @GetMapping("/admin/channelreport")
    public String viewChannelReport(){
        return "thymeleaf/admin/report/channels";
    }

    @GetMapping("/admin/appointmentreport")
    public String viewAppointmentReport(){
        return "thymeleaf/admin/report/appointments";
    }



//    Doctor Navigation
    @GetMapping("/doctor/today")
    public String viewTodayAppointments(){
        return "thymeleaf/doctor/appointment/today";
    }


    @GetMapping("/doctor/channels")
    public String viewUpcomingChannels(){
        return "thymeleaf/doctor/channel/channels";
    }


//    Receptionist Navigation
    @GetMapping("/receptionist/patient")
    public String viewAllPatients(){
        return "thymeleaf/receptionist/patient/patients";
    }

    @GetMapping("/receptionist/appointment")
    public String viewAllAppointments(){
        return "thymeleaf/receptionist/appointment/appointments";
    }


//    Patient Navigation

    @GetMapping("/patient/medication")
    public String viewPatientMedicaions(){
        return "thymeleaf/patient/medication/medications";
    }

    @GetMapping("/patient/prescription")
    public String viewPatientPrescriptions(){
        return "thymeleaf/patient/prescription/prescriptions";
    }

    @GetMapping("/patient/allergy")
    public String viewPatientAllergies(){
        return "thymeleaf/patient/allergy/allergies";
    }

    @GetMapping("/patient/appointment")
    public String viewPatientApp(){
        return "thymeleaf/patient/appointment/appointments";
    }

    @GetMapping("/patient/channel")
    public String viewPatientChan(){
        return "thymeleaf/patient/channel/channels";
    }


//    Lab Teschnician

    @GetMapping("/labtechnician/labtest")
    public String viewLabTests(){
        return "thymeleaf/lab_technician/labtest/labtests";
    }



//    Pharmacist

    @GetMapping("/pharmacist/order")
    public String viewOrders(){
        return "thymeleaf/pharmacist/order/orders";
    }


}
