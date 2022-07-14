package com.app.AlistApp.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Service implements Serializable {
    private int id;
    private String name;
    private int iconId;
    private ArrayList<SubService>subServices;

    public Service(int id, String name, int iconId, ArrayList<SubService> subServices) {
        this.id = id;
        this.name = name;
        this.iconId = iconId;
        this.subServices = subServices;
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

    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }

    public ArrayList<SubService> getSubServices() {
        return subServices;
    }

    public void setSubServices(ArrayList<SubService> subServices) {
        this.subServices = subServices;
    }
}
