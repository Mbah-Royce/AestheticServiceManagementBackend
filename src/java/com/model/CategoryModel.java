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
public class CategoryModel {

    private int categoryid;
    private int serviceid;
    private String categoryname;
    private double price;
    private String desc;
    private int duration;
    private String categorystate;
    private Timestamp categorycreatedat;
    private Timestamp categoryupdateat;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
    
    public int getCategoryid() {
        return categoryid;
    }

    public void setCategoryid(int categoryid) {
        this.categoryid = categoryid;
    }

    public int getServiceid() {
        return serviceid;
    }

    public void setServiceid(int serviceid) {
        this.serviceid = serviceid;
    }

    public String getCategoryname() {
        return categoryname;
    }

    public void setCategoryname(String categoryname) {
        this.categoryname = categoryname;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCategorystate() {
        return categorystate;
    }

    public void setCategorystate(String categorystate) {
        this.categorystate = categorystate;
    }

    public Timestamp getCategorycreatedat() {
        return categorycreatedat;
    }

    public void setCategorycreatedat(Timestamp categorycreatedat) {
        this.categorycreatedat = categorycreatedat;
    }

    public Timestamp getCategoryupdateat() {
        return categoryupdateat;
    }

    public void setCategoryupdateat(Timestamp categoryupdateat) {
        this.categoryupdateat = categoryupdateat;
    }
    
    
}
