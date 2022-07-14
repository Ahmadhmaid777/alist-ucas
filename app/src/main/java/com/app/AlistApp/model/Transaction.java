package com.app.AlistApp.model;

import java.util.Date;

public class Transaction {
    private int userId;
    private int id;
    private String name;
    private String type;
    private String requestDate;
    private String state;
    private float rate;

    public Transaction(int userId, int id, String name, String type, String requestDate, String state, float rate) {
        this.userId = userId;
        this.id = id;
        this.name = name;
        this.type = type;
        this.requestDate = requestDate;
        this.state = state;
        this.rate = rate;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(String requestDate) {
        this.requestDate = requestDate;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
