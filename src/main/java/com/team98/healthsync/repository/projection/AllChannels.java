package com.team98.healthsync.repository.projection;

import com.team98.healthsync.enums.ChannelState;
import com.team98.healthsync.models.Specialization;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.text.SimpleDateFormat;
import java.util.Date;

public interface AllChannels {
    String getId();

    @Temporal(TemporalType.TIMESTAMP)
    Date getDateTime();

    default String getDate(){
        SimpleDateFormat sd = new SimpleDateFormat("MM/dd/yyyy");
        String dateStr = sd.format(getDateTime());
        return dateStr;
    }

    default String getTime(){
        SimpleDateFormat sd = new SimpleDateFormat("hh:mm a");
        String timeStr = sd.format(getDateTime());
        return timeStr;
    }

    default String getDay(){
        SimpleDateFormat sd = new SimpleDateFormat("EEEE");
        String dayStr = sd.format(getDateTime());
        return dayStr;
    }


    ChannelState getState();

    long getPatientCount();


    Doctor getDoctor();

    interface Doctor {
        String getFirstName();

        String getLastName();

        String getQualification();

        Specialization getSpecialization();

        default String getFullName(){
            return "Dr." + getFirstName() + " " + getLastName();
        }
    }


}
