package com.app.AlistApp.model;

import java.io.Serializable;

public class SubService implements Serializable {
    private int id;
    private String name;

    public SubService(int id, String name) {
        this.id = id;
        this.name = name;
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
}
