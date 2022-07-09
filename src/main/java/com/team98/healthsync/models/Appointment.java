package com.team98.healthsync.models;

import com.team98.healthsync.enums.AppointmentState;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
public class   Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    @OneToOne()
    @JoinColumn(name = "channel_id",referencedColumnName = "id")
    private Channel channel;


    @OneToOne()
    @JoinColumn(name = "patient_id",referencedColumnName = "id")
    private Patient patient;

    @Enumerated(EnumType.STRING)
    private AppointmentState  state;

    private long appointmentNo;

    @Transient
    @NotEmpty(message = "Enter a patient Id")
    private  String patientId;







    public Appointment() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public  String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public long getAppointmentNo() {
        return appointmentNo;
    }

    public void setAppointmentNo(long appointmentNo) {
        this.appointmentNo = appointmentNo;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public AppointmentState getState() {
        return state;
    }

    public void setState(AppointmentState state) {
        this.state = state;
    }


}
