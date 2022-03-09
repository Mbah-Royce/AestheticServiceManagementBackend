/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model;

import java.sql.Timestamp;

/**
 *
 * @author Mbah Royce
 */
public class ServiceModel {
    private int serviceid;
    private String name;
    private String description;
    private String servicestate;
    private Timestamp servicecreatedat;
    private Timestamp serviceupdatedat;
    private String imagepath;

    public int getServiceid() {
        return serviceid;
    }

    public void setServiceid(int serviceid) {
        this.serviceid = serviceid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getServicestate() {
        return servicestate;
    }

    public void setServicestate(String servicestate) {
        this.servicestate = servicestate;
    }

    public Timestamp getServicecreatedat() {
        return servicecreatedat;
    }

    public void setServicecreatedat(Timestamp servicecreatedat) {
        this.servicecreatedat = servicecreatedat;
    }

    public Timestamp getServiceupdatedat() {
        return serviceupdatedat;
    }

    public void setServiceupdatedat(Timestamp serviceupdatedat) {
        this.serviceupdatedat = serviceupdatedat;
    }

    public String getImagepath() {
        return imagepath;
    }

    public void setImagepath(String imagepath) {
        this.imagepath = imagepath;
    }
    
    
}
