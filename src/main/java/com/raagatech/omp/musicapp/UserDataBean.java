/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.raagatech.omp.musicapp;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author sarve
 */
public class UserDataBean {

    private int userId;
    private String userName;
    private String password;
    private long mobile;
    private String email;
    private int countryCode;
    private String address;
    private Date creationDate;
    private int pincode;
    private int inspiratorId;
    private char gender;
    private int mobileVerified;
    private Set<String> inspiratorSet = new HashSet<>();

    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the mobile
     */
    public long getMobile() {
        return mobile;
    }

    /**
     * @param mobile the mobile to set
     */
    public void setMobile(long mobile) {
        this.mobile = mobile;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the countryCode
     */
    public int getCountryCode() {
        return countryCode;
    }

    /**
     * @param countryCode the countryCode to set
     */
    public void setCountryCode(int countryCode) {
        this.countryCode = countryCode;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return the creationDate
     */
    public Date getCreationDate() {
        return creationDate;
    }

    /**
     * @param creationDate the creationDate to set
     */
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * @return the userId
     */
    public int getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * @return the pincode
     */
    public int getPincode() {
        return pincode;
    }

    /**
     * @param pincode the pincode to set
     */
    public void setPincode(int pincode) {
        this.pincode = pincode;
    }

    /**
     * @return the inspiratorId
     */
    public int getInspiratorId() {
        return inspiratorId;
    }

    /**
     * @param inspiratorId the inspiratorId to set
     */
    public void setInspiratorId(int inspiratorId) {
        this.inspiratorId = inspiratorId;
    }

    /**
     * @return the gender
     */
    public char getGender() {
        return gender;
    }

    /**
     * @param gender the gender to set
     */
    public void setGender(char gender) {
        this.gender = gender;
    }    

    /**
     * @return the mobileVerified
     */
    public int getMobileVerified() {
        return mobileVerified;
    }

    /**
     * @param mobileVerified the mobileVerified to set
     */
    public void setMobileVerified(int mobileVerified) {
        this.mobileVerified = mobileVerified;
    }

    /**
     * @return the inspiratorSet
     */
    public Set<String> getInspiratorSet() {
        return inspiratorSet;
    }

    /**
     * @param inspiratorSet the inspiratorSet to set
     */
    public void setInspiratorSet(Set<String> inspiratorSet) {
        this.inspiratorSet = inspiratorSet;
    }    
            
}
