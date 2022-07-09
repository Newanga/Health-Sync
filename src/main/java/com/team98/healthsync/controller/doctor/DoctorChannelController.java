package com.team98.healthsync.controller.doctor;

import com.team98.healthsync.repository.ChannelRepository;
import com.team98.healthsync.repository.projection.AllChannels;
import com.team98.healthsync.service.IDocHelper;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

@Controller
public class DoctorChannelController {

    @Autowired
    private ChannelRepository channelRepository;

    @Autowired
    private IDocHelper Helper;


    @GetMapping("/doctor/upcomingdoctorchannels")
    @ResponseBody
    public List<AllChannels> getAllDoctorChannelsUpcoming(){
        DateTime todayAtMidnight = new DateTime().withTimeAtStartOfDay();
        Date dateTomorrow=todayAtMidnight.toDate();
        dateTomorrow.setTime(dateTomorrow.getTime()+86400000);
        return channelRepository.findAllByDoctor_IdAndDateTimeGreaterThan(Helper.GetDoctorId(),dateTomorrow);
    }
}
