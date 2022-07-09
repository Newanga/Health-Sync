package com.team98.healthsync.repository;

import com.team98.healthsync.models.Appointment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface AppointmentRepository extends CrudRepository<Appointment,Long> {

    List<Appointment> findByChannelId(long id);


    List<Appointment> findAllByChannelId_DoctorId(long id);

    List<Appointment> findAllByChannel_IdAndPatient_Id(long channelid,long patientId);

    List<Appointment> findAllByChannelId_DoctorIdAndChannelId_DateTimeBetween(long id, Date startDate,Date endDate);

    List<Appointment> findAllByPatient_IdAndChannel_DateTimeBefore(long id, Date today);

    List<Appointment> findAllByPatient_IdAndChannel_DateTimeAfter(long id, Date today);



}
