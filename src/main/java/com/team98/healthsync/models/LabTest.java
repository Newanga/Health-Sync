package com.team98.healthsync.models;

import com.team98.healthsync.enums.LabTestState;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Entity
public class LabTest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne()
    @JoinColumn(name = "category_id",referencedColumnName = "id")
    private LabTestCategory category;

    @Enumerated(EnumType.STRING)
    private LabTestState state;

    @DateTimeFormat(pattern = "dd/MM/yyyy h:mm a")
    @Temporal(TemporalType.TIMESTAMP)
    private Date receivedDate;


    @DateTimeFormat(pattern = "dd/MM/yyyy h:mm a")
    @Temporal(TemporalType.TIMESTAMP)
    private Date collectedDate;

    @OneToOne()
    @JoinColumn(name = "patient_id",referencedColumnName = "id")
    private Patient patient;

    @Lob
    @Transient
    private byte[] report;

    @Transient
    @NotEmpty(message = "Enter a patient Id")
    private  String patientId;

    private  String fileName;


    public LabTest() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LabTestCategory getCategory() {
        return category;
    }

    public void setCategory(LabTestCategory category) {
        this.category = category;
    }

    public LabTestState getState() {
        return state;
    }

    public void setState(LabTestState state) {
        this.state = state;
    }

    public Date getReceivedDate() {
        return receivedDate;
    }

    public void setReceivedDate(Date receivedDate) {
        this.receivedDate = receivedDate;
    }

    public Date getCollectedDate() {
        return collectedDate;
    }

    public void setCollectedDate(Date collectedDate) {
        this.collectedDate = collectedDate;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public byte[] getReport() {
        return report;
    }

    public void setReport(byte[] report) {
        this.report = report;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
