package com.team98.healthsync.models;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
public class Allergy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne()
    @JoinColumn(name = "appointment_id",referencedColumnName = "id")
    private Appointment appointment;

    @NotEmpty(message = "Enter Title ")
    private String title;
    @NotEmpty(message = "Enter description")
    private String description;

    public Allergy() {
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
