/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.raagatech.omp.musicapp;

import java.util.Date;
import java.util.List;

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
    private List<String> inspiratorList;
    private int discount;
    private String specialization;
    private List<String> inspirationList;
    private List<String> examFeesList;
    private List<String> inquiryStatusList;

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
     * @return the inspiratorList
     */
    public List<String> getInspiratorList() {
        return inspiratorList;
    }

    /**
     * @param inspiratorList the inspiratorList to list
     */
    public void setInspiratorList(List<String> inspiratorList) {
        this.inspiratorList = inspiratorList;
    }

    /**
     * @return the discount
     */
    public int getDiscount() {
        return discount;
    }

    /**
     * @param discount the discount to set
     */
    public void setDiscount(int discount) {
        this.discount = discount;
    }

    /**
     * @return the specialization
     */
    public String getSpecialization() {
        return specialization;
    }

    /**
     * @param specialization the specialization to set
     */
    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    /**
     * @return the inspirationList
     */
    public List<String> getInspirationList() {
        return inspirationList;
    }

    /**
     * @param inspirationList the inspirationList to set
     */
    public void setInspirationList(List<String> inspirationList) {
        this.inspirationList = inspirationList;
    }

    /**
     * @return the examFeesList
     */
    public List<String> getExamFeesList() {
        return examFeesList;
    }

    /**
     * @param examFeesList the examFeesList to set
     */
    public void setExamFeesList(List<String> examFeesList) {
        this.examFeesList = examFeesList;
    }

    /**
     * @return the inquiryStatusList
     */
    public List<String> getInquiryStatusList() {
        return inquiryStatusList;
    }

    /**
     * @param inquiryStatusList the inquiryStatusList to set
     */
    public void setInquiryStatusList(List<String> inquiryStatusList) {
        this.inquiryStatusList = inquiryStatusList;
    }

}
