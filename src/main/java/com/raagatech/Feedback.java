/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.raagatech;

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
        return "Name: " + name + " Email: " + email + " Mobile: " + mobile + " Feedback: " + followupDetails;
    }

}
