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
public class UserModel {

    private int userid;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private String status;
    private Timestamp verifiedat;
    private String gender;
    private String state;
    private Timestamp usercreatedat;
    private Timestamp userupdateat;
    private String profilepicture;
    private String phone;
    private String address;

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getVerifiedat() {
        return verifiedat;
    }

    public void setVerifiedat(Timestamp verifiedat) {
        this.verifiedat = verifiedat;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Timestamp getUsercreatedat() {
        return usercreatedat;
    }

    public void setUsercreatedat(Timestamp usercreatedat) {
        this.usercreatedat = usercreatedat;
    }

    public Timestamp getUserupdateat() {
        return userupdateat;
    }

    public void setUserupdateat(Timestamp userupdateat) {
        this.userupdateat = userupdateat;
    }

    public String getProfilepicture() {
        return profilepicture;
    }

    public void setProfilepicture(String profilepicture) {
        this.profilepicture = profilepicture;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    
    
}
