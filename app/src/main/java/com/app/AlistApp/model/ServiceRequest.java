package com.app.AlistApp.model;

import java.util.Date;

public class ServiceRequest {
    private Date serviceDate;
    private String applicantName;
    private String applicantJob;
    private String applicant;
    private long cardId;
    private String notes;
    private String nearestLandmark;
    private String phoneNumber;


    public ServiceRequest( Date serviceDate, String applicantName, String applicantJob, String applicant, long cardId, String notes, String nearestLandmark, String phoneNumber) {
        this.serviceDate = serviceDate;
        this.applicantName = applicantName;
        this.applicantJob = applicantJob;
        this.applicant = applicant;
        this.cardId = cardId;
        this.notes = notes;
        this.nearestLandmark = nearestLandmark;
        this.phoneNumber = phoneNumber;
    }

    public Date getServiceDate() {
        return serviceDate;
    }

    public void setServiceDate(Date serviceDate) {
        this.serviceDate = serviceDate;
    }

    public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }

    public String getApplicantJob() {
        return applicantJob;
    }

    public void setApplicantJob(String applicantJob) {
        this.applicantJob = applicantJob;
    }

    public String getApplicant() {
        return applicant;
    }

    public void setApplicant(String applicant) {
        this.applicant = applicant;
    }

    public long getCardId() {
        return cardId;
    }

    public void setCardId(long cardId) {
        this.cardId = cardId;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getNearestLandmark() {
        return nearestLandmark;
    }

    public void setNearestLandmark(String nearestLandmark) {
        this.nearestLandmark = nearestLandmark;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
