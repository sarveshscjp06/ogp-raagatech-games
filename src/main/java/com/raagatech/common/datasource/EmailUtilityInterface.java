/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.raagatech.common.datasource;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 *
 * @author sarve
 */
public interface EmailUtilityInterface {

    public void sendSimpleMail();
    
    public void sendEmail(String toEmail, String subject, String body);

    public void sendGoogleMail(String email, String inquiryname, String followupDetails) throws IOException;

    public void executeCronJob(String subject, String followupDetails) throws UnsupportedEncodingException, IOException;

    public void executeHbdJob(String subject, String followupDetails, Date dob) throws UnsupportedEncodingException, IOException;

    public void pushSalesAndServicesViaEmail(String vendorBrandName, String fromEmail, String email, String subject, String offer, String inquiryname) throws IOException;
}
