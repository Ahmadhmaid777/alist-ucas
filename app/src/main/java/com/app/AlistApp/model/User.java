package com.app.AlistApp.model;

public class User {

    int id;
    String name;
    int job_id;
    String phoneNumber;
    String nearestLandmark;

    public User(int id, String name, int job_id, String phoneNumber, String nearestLandmark) {
        this.id = id;
        this.name = name;
        this.job_id = job_id;
        this.phoneNumber = phoneNumber;
        this.nearestLandmark = nearestLandmark;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getJob_id() {
        return job_id;
    }

    public void setJob_id(int job_id) {
        this.job_id = job_id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getNearestLandmark() {
        return nearestLandmark;
    }

    public void setNearestLandmark(String nearestLandmark) {
        this.nearestLandmark = nearestLandmark;
    }
}
