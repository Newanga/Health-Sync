package com.team98.healthsync.controller.admin;

import com.team98.healthsync.enums.ChannelState;
import com.team98.healthsync.models.Channel;
import com.team98.healthsync.models.Doctor;
import com.team98.healthsync.repository.ChannelRepository;
import com.team98.healthsync.repository.DoctorRepository;
import com.team98.healthsync.repository.projection.AllChannels;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Controller
public class ChannelController {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private ChannelRepository channelRepository;

    @GetMapping("/admin/channels/new")
    public ModelAndView newChannel(){
        ModelAndView mv=new ModelAndView();
        mv.addObject("doctors_dropdown",doctorRepository.findAll());
        mv.addObject("channel",new Channel());
        mv.setViewName("thymeleaf/admin/channel/new_channel");
        return mv;
    }

    @GetMapping("/admin/channels")
    @ResponseBody
    public List<AllChannels> getAllChannels(){

        return channelRepository.findBy();
    }

    @GetMapping("/admin/upcomingchannels")
    @ResponseBody
    public List<AllChannels> getAllChannelsUpcoming(){
        DateTime todayAtMidnight = new DateTime().withTimeAtStartOfDay();
        Date date=todayAtMidnight.toDate();
        return channelRepository.findByDateTimeGreaterThan(date);
    }

    @GetMapping("/admin/channelstoday")
    @ResponseBody
    public List<AllChannels> getAllChannelsToday(){
        DateTime todayAtMidnight = new DateTime().withTimeAtStartOfDay();
        Date dateToday=todayAtMidnight.toDate();
        Date dateTomorrow=todayAtMidnight.toDate();
        dateTomorrow.setTime(dateTomorrow.getTime()+86400000);
       return channelRepository.findAllByDateTimeBetween(dateToday,dateTomorrow);
    }


    @PostMapping("/admin/channels")
    public ModelAndView createNewChannel(@Valid @ModelAttribute("channel") Channel channel, BindingResult result  ){
        if(result.hasErrors()) {
            ModelAndView mv=new ModelAndView();
            mv.addObject("doctors_dropdown",doctorRepository.findBy());
            mv.addObject("channel",channel);
            mv.setViewName("thymeleaf/admin/channel/new_channel");
            return mv;
        }
        List<AllChannels>  channels=channelRepository.findByDateTime(channel.getDateTime());
        if(channels.spliterator().getExactSizeIfKnown()!=0){
            return new ModelAndView("thymeleaf/admin/channel/channels", "error", "The doctor has an already booked channel on that date.");
        }


        try {
            Doctor doctor=doctorRepository.findById(channel.getDocId()).orElseThrow();
            channel.setDoctor(doctor);
            Channel savedChannel= channelRepository.save(channel);
        } catch (Exception e) {
            return new ModelAndView("thymeleaf/admin/channel/channels", "error", e.getMessage());
        }
        return new ModelAndView("redirect:/admin/channel/","success","true");

    }


    @GetMapping("/admin/channels/edit/{id}")
    public ModelAndView editChannelById(@PathVariable("id") @NotNull @Min(value = 0) long id){
       Channel channel=channelRepository.findById(id).orElseThrow();
        String fullName= String.format("Dr. %s %s",channel.getDoctor().getFirstName(),channel.getDoctor().getLastName());
        channel.getDoctor().setFullName(fullName);
        ModelAndView mv=new ModelAndView();
        mv.addObject("channel",channel);
        return new ModelAndView("thymeleaf/admin/channel/edit_channel","channel",channel);
    }


    @PostMapping("/admin/channels/edit")
    @Transactional
    public ModelAndView editChannel(@ModelAttribute("id")  long id,
                                    @ModelAttribute("state") ChannelState state){

        Channel channel=channelRepository.findById(id).orElse(null);
        channel.setDocId(channel.getDoctor().getId());
        channel.setState(state);

        if(channel==null){
            return new ModelAndView("thymeleaf/admin/channel/channels", "error", "Channel do not exit.");
        }else {


            try {
                channelRepository.save(channel);
            }
            catch (Exception ex){
                return new ModelAndView("redirect:/admin/channel","error","fail");
            }

            return new ModelAndView("redirect:/admin/channel","update","true");
        }


    }


    @GetMapping("/admin/channels/{id}")
    @ResponseBody
    public  ModelAndView getChannelById(@PathVariable("id") @NotNull @Min(value = 0) long id){
        Channel channel=channelRepository.findById(id).orElseThrow();
        String fullName= String.format("Dr. %s %s",channel.getDoctor().getFirstName(),channel.getDoctor().getLastName());
        channel.getDoctor().setFullName(fullName);
        ModelAndView mv=new ModelAndView();
        mv.addObject("channel",channel);
        return new ModelAndView("thymeleaf/admin/channel/view_channel","channel",channel);
    }


}
