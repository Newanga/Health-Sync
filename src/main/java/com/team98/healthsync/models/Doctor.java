package com.team98.healthsync.models;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "DOCTOR")
public class Doctor extends Person {

    @NotEmpty(message = "Enter Specialization")
    private String qualification;

    @OneToOne()
    @JoinColumn(name = "specialization_id",referencedColumnName = "id")
    private Specialization specialization;


    @NotEmpty(message = "Enter allocated room")
    private String room;

    @Transient
    private Long specializationId;

    @Transient
    private String fullName;

    public Doctor() {
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public Specialization getSpecialization() {
        return specialization;
    }

    public void setSpecialization(Specialization specialization) {
        this.specialization = specialization;
    }

    public Long getSpecializationId() {
        return specializationId;
    }

    public void setSpecializationId(Long specializationId) {
        this.specializationId = specializationId;
    }

    public String getQualification() {
        return qualification;
    }


    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }
}
