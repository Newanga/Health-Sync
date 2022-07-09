package com.team98.healthsync.models;

import com.team98.healthsync.enums.OrderState;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
public class PharmacyOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne()
    @JoinColumn(name = "prescription_id",referencedColumnName = "id")
    private Prescription prescription;

    @Enumerated(EnumType.STRING)
    private OrderState state;

    @DateTimeFormat(pattern = "dd/MM/yyyy h:mm a")
    @NotNull(message = "Select a channel date and time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateTime;

    public PharmacyOrder() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Prescription getPrescription() {
        return prescription;
    }

    public void setPrescription(Prescription prescription) {
        this.prescription = prescription;
    }

    public OrderState getState() {
        return state;
    }

    public void setState(OrderState state) {
        this.state = state;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }
}
