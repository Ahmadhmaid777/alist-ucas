package com.app.AlistApp.model;

import android.text.TextUtils;

import java.io.Serializable;
import java.util.Date;

public class TransactionFilters implements Serializable {
    private String serviceType;
    private String serviceState;
    private Date startDate;
    private Date endDate;

    public TransactionFilters(String serviceType, String serviceState, Date startDate, Date endDate) {
        this.serviceType = serviceType;
        this.serviceState = serviceState;
        this.startDate = startDate;
        this.endDate = endDate;
    }


    public boolean isFiltersNull(){
        if (TextUtils.isEmpty(serviceType)&&TextUtils.isEmpty(serviceState)&&(startDate==null||endDate==null)){
            return true;
        }
        return false;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getServiceState() {
        return serviceState;
    }

    public void setServiceState(String serviceState) {
        serviceState = serviceState;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
