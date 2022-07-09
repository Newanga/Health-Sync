package com.team98.healthsync.repository;

import com.team98.healthsync.models.Channel;
import com.team98.healthsync.repository.projection.AllChannels;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ChannelRepository extends CrudRepository<Channel,Long> {

    List<AllChannels> findBy();

    List<AllChannels>  findByDateTimeGreaterThan(Date dateTime);

    List<AllChannels>  findByDateTime(Date dateTime);

    List<AllChannels>   findAllByDateTimeBetween(Date startDate,Date endDate);

    List<AllChannels>  findAllByDoctor_IdAndDateTimeGreaterThan(long docId,Date date);



}
