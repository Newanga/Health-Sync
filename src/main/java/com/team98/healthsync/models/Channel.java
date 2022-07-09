package com.team98.healthsync.models;

import com.team98.healthsync.enums.ChannelState;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
public class Channel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @DateTimeFormat(pattern = "dd/MM/yyyy h:mm a")
    @NotNull(message = "Select a channel date and time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateTime;

    @Enumerated(EnumType.STRING)
    private ChannelState state;

    @OneToOne()
    @JoinColumn(name = "doctor_id",referencedColumnName = "id")
    private Doctor doctor;

    @Value("${some.key:0}")
    private long patientCount;


    @NotNull(message = "Enter channel charge amount")
    private Integer charge;


    @Transient
    private Long docId;

    public Channel() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public ChannelState getState() {
        return state;
    }

    public void setState(ChannelState state) {
        this.state = state;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Long getDocId() {
        return docId;
    }

    public void setDocId(Long docId) {
        this.docId = docId;
    }

    public long getPatientCount() {
        return patientCount;
    }

    public void setPatientCount(long patientCount) {
        this.patientCount = patientCount;
    }

    public Integer getCharge() {
        return charge;
    }

    public void setCharge(Integer charge) {
        this.charge = charge;
    }
}
