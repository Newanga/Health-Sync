package com.team98.healthsync.models;

public class Dashboard {

    private int patientCount;

    private int receptionistCount;

    private int pharmacistCount;

    private int labTechnicianCount;

    private int doctorCount;

    private int channelsToday;

    private int appointmentsToday;

    public Dashboard() {
    }

    public int getPatientCount() {
        return patientCount;
    }

    public void setPatientCount(int patientCount) {
        this.patientCount = patientCount;
    }

    public int getReceptionistCount() {
        return receptionistCount;
    }

    public void setReceptionistCount(int receptionistCount) {
        this.receptionistCount = receptionistCount;
    }

    public int getPharmacistCount() {
        return pharmacistCount;
    }

    public void setPharmacistCount(int pharmacistCount) {
        this.pharmacistCount = pharmacistCount;
    }

    public int getLabTechnicianCount() {
        return labTechnicianCount;
    }

    public void setLabTechnicianCount(int labTechnicianCount) {
        this.labTechnicianCount = labTechnicianCount;
    }

    public int getDoctorCount() {
        return doctorCount;
    }

    public void setDoctorCount(int doctorCount) {
        this.doctorCount = doctorCount;
    }

    public int getChannelsToday() {
        return channelsToday;
    }

    public void setChannelsToday(int channelsToday) {
        this.channelsToday = channelsToday;
    }

    public int getAppointmentsToday() {
        return appointmentsToday;
    }

    public void setAppointmentsToday(int appointmentsToday) {
        this.appointmentsToday = appointmentsToday;
    }
}
