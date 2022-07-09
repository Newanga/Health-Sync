package com.team98.healthsync.controller.admin;

import com.team98.healthsync.models.Dashboard;
import com.team98.healthsync.repository.*;
import com.team98.healthsync.repository.projection.AllChannels;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Controller
public class DashBoardController {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private LabTechnicianRepository labTechnicianRepository;

    @Autowired
    private PharmacistRepository pharmacistRepository;

    @Autowired
    private ReceptionistRepository receptionistRepository;

    @Autowired
    private ChannelRepository channelRepository;


    @GetMapping("/admin/dashboard")
    @Transactional
    public ModelAndView viewDashboard(){
        Dashboard dash=new Dashboard();
        dash.setDoctorCount((int)doctorRepository.findAll().spliterator().getExactSizeIfKnown());
        dash.setLabTechnicianCount((int)labTechnicianRepository.findAll().spliterator().getExactSizeIfKnown());
        dash.setPatientCount((int)patientRepository.findAll().spliterator().getExactSizeIfKnown());
        dash.setReceptionistCount((int)receptionistRepository.findAll().spliterator().getExactSizeIfKnown());
        dash.setPharmacistCount((int)pharmacistRepository.findAll().spliterator().getExactSizeIfKnown());


        DateTime todayAtMidnight = new DateTime().withTimeAtStartOfDay();
        Date dateToday=todayAtMidnight.toDate();
        Date dateTomorrow=todayAtMidnight.toDate();
        dateTomorrow.setTime(dateTomorrow.getTime()+86400000);
        List<AllChannels> channels=channelRepository.findAllByDateTimeBetween(dateToday,dateTomorrow);
        dash.setChannelsToday((int)channels.spliterator().getExactSizeIfKnown());

        var appointmentCount=0;
        for (AllChannels channel:channels) {
            appointmentCount=appointmentCount+ ((int)channel.getPatientCount());
        }
        dash.setAppointmentsToday(appointmentCount);

        ModelAndView mv=new ModelAndView();
        mv.addObject("dash",dash);
        mv.setViewName("thymeleaf/admin/dashboard/dashboard");
        return mv;
    }

    @GetMapping("/admin/upcoming")
    @ResponseBody
    public List<AllChannels> getDashboardChannels(){
        DateTime todayAtMidnight = new DateTime().withTimeAtStartOfDay();
        Date date=todayAtMidnight.toDate();
        return channelRepository.findByDateTimeGreaterThan(date);
    }



}
