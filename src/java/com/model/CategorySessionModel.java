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
public class CategorySessionModel {
    
    private String sessionid;
    private String starttime;
    private String endtime;
    private String sessionname;
    private int categoryid;
    private Timestamp sessioncreateddat;
    private Timestamp sessionupdatedat;

    public String getSessionid() {
        return sessionid;
    }

    public void setSessionid(String sessionid) {
        this.sessionid = sessionid;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public int getCategoryid() {
        return categoryid;
    }

    public void setCategoryid(int categoryid) {
        this.categoryid = categoryid;
    }

    public String getSessionname() {
        return sessionname;
    }

    public void setSessionname(String sessionname) {
        this.sessionname = sessionname;
    }

    public Timestamp getSessioncreateddat() {
        return sessioncreateddat;
    }

    public void setSessioncreateddat(Timestamp sessioncreateddat) {
        this.sessioncreateddat = sessioncreateddat;
    }

    public Timestamp getSessionupdatedat() {
        return sessionupdatedat;
    }

    public void setSessionupdatedat(Timestamp sessionupdatedat) {
        this.sessionupdatedat = sessionupdatedat;
    }
    
    
}
