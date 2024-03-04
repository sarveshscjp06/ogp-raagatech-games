package com.raagatech;

public class Appointment {

    private long id;
    private String content;

    private String name;
    private String email;
    private long phone;
    private String followupDetails;
    private String wayToReach;
    private String monday;
    private String tuesday;
    private String wednesday;
    private String thursday;
    private String friday;
    private String availabilityMorning;
    private String availabilityAfternoon;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

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
     * @return the phone
     */
    public long getPhone() {
        return phone;
    }

    /**
     * @param phone the phone to set
     */
    public void setPhone(long phone) {
        this.phone = phone;
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

    /**
     * @return the wayToReach
     */
    public String getWayToReach() {
        return wayToReach;
    }

    /**
     * @param wayToReach the wayToReach to set
     */
    public void setWayToReach(String wayToReach) {
        this.wayToReach = wayToReach;
    }

    /**
     * @return the monday
     */
    public String getMonday() {
        return monday;
    }

    /**
     * @param monday the monday to set
     */
    public void setMonday(String monday) {
        this.monday = monday;
    }

    /**
     * @return the tuesday
     */
    public String getTuesday() {
        return tuesday;
    }

    /**
     * @param tuesday the tuesday to set
     */
    public void setTuesday(String tuesday) {
        this.tuesday = tuesday;
    }

    /**
     * @return the wednesday
     */
    public String getWednesday() {
        return wednesday;
    }

    /**
     * @param wednesday the wednesday to set
     */
    public void setWednesday(String wednesday) {
        this.wednesday = wednesday;
    }

    /**
     * @return the thursday
     */
    public String getThursday() {
        return thursday;
    }

    /**
     * @param thursday the thursday to set
     */
    public void setThursday(String thursday) {
        this.thursday = thursday;
    }

    /**
     * @return the friday
     */
    public String getFriday() {
        return friday;
    }

    /**
     * @param friday the friday to set
     */
    public void setFriday(String friday) {
        this.friday = friday;
    }

    /**
     * @return the availabilityMorning
     */
    public String getAvailabilityMorning() {
        return availabilityMorning;
    }

    /**
     * @param availabilityMorning the availabilityMorning to set
     */
    public void setAvailabilityMorning(String availabilityMorning) {
        this.availabilityMorning = availabilityMorning;
    }

    /**
     * @return the availabilityAfternoon
     */
    public String getAvailabilityAfternoon() {
        return availabilityAfternoon;
    }

    /**
     * @param availabilityAfternoon the availabilityAfternoon to set
     */
    public void setAvailabilityAfternoon(String availabilityAfternoon) {
        this.availabilityAfternoon = availabilityAfternoon;
    }
        
}