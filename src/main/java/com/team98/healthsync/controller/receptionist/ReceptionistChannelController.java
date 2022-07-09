package com.team98.healthsync.controller.receptionist;

import com.team98.healthsync.repository.ChannelRepository;
import com.team98.healthsync.repository.projection.AllChannels;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

@Controller
public class ReceptionistChannelController {

    @Autowired
    private ChannelRepository channelRepository;


    @GetMapping("/receptionist/upcomingchannels")
    @ResponseBody
    public List<AllChannels> getAllChannelsUpcoming(){
        DateTime todayAtMidnight = new DateTime().withTimeAtStartOfDay();
        Date date=todayAtMidnight.toDate();
        return channelRepository.findByDateTimeGreaterThan(date);
    }
}
