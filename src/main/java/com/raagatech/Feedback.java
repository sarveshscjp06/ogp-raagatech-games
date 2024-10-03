/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.raagatech;

import java.util.Date;

/**
 *
 * @author STripathi
 */
public class Feedback {

    private long id;
    private String content;
    private String name;
    private String email;
    private long mobile;
    private String followupDetails;
    private Date followup_date;

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return the content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content the content to set
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
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
     * @return the followupDetails
     */
    public String getFollowupDetails() {
        return followupDetails;
    }

    /**
     * @param followupDetails the followupDetails to set
     */
    public void setFollowupDetails(String followupDetails) {
        this.followupDetails = followupDetails;
    }
    
    

    @Override
    public String toString() {
        return "Name: " + name + " Email: " + email + " Mobile: " + mobile + " Feedback: " + followupDetails  + " Feedback date: " +followup_date;
    }

    /**
     * @return the followup_date
     */
    public Date getFollowup_date() {
        return followup_date;
    }

    /**
     * @param followup_date the followup_date to set
     */
    public void setFollowup_date(Date followup_date) {
        this.followup_date = followup_date;
    }

}
