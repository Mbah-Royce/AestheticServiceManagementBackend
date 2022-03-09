/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model;

import java.sql.Timestamp;
import java.util.Date;

/**
 *
 * @author Mbah Royce
 */
public class AppointmentModel {
    private String appointmentid;
    private int sessionid;
    private String userid;
    private String appointmentdate;
    private String appointmentstatus;
    private Timestamp appointmentcreateat;
    private Timestamp appointmentupdateat;
    private boolean ispaid;

    public String getAppointmentdate() {
        return appointmentdate;
    }

    public void setAppointmentdate(String appointmentdate) {
        this.appointmentdate = appointmentdate;
    }

    public String getAppointmentid() {
        return appointmentid;
    }

    public void setAppointmentid(String appointmentid) {
        this.appointmentid = appointmentid;
    }

    public int getSessionid() {
        return sessionid;
    }

    public void setSessionid(int sessionid) {
        this.sessionid = sessionid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }


    public String getAppointmentstatus() {
        return appointmentstatus;
    }

    public void setAppointmentstatus(String appointmentstatus) {
        this.appointmentstatus = appointmentstatus;
    }

    public Timestamp getAppointmentcreateat() {
        return appointmentcreateat;
    }

    public void setAppointmentcreateat(Timestamp appointmentcreateat) {
        this.appointmentcreateat = appointmentcreateat;
    }

    public Timestamp getAppointmentupdateat() {
        return appointmentupdateat;
    }

    public void setAppointmentupdateat(Timestamp appointmentupdateat) {
        this.appointmentupdateat = appointmentupdateat;
    }

    public boolean isIspaid() {
        return ispaid;
    }

    public void setIspaid(boolean ispaid) {
        this.ispaid = ispaid;
    }

    
}
