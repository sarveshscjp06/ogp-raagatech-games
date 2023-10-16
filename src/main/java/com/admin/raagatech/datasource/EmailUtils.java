/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.admin.raagatech.datasource;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

/**
 * https://www.codejava.net/frameworks/spring-boot/email-sending-tutorial
 * https://mkyong.com/spring-boot/spring-boot-how-to-send-email-via-smtp/
 * @author sarve
 */
public class EmailUtils implements EmailUtilityInterface {

    @Autowired
    private JavaMailSender javaMailSender;

    private static final String SIGNATURE = "\n\n\n\n\n\nRaksha Tripathi\n"
            + "'PRAVEEN' Indian Classical Vocal & Instrumental\n"
            + "Principal / Superintendent\n"
            + "raagatech 'The World of Music Education & Performance' \n"
            + "(An authorized exam & study centre for PRAYAG SANGIT SAMITI, ALLAHABAD)\n"
            + "Land-Line: +91 120 4276874 Mobile: +919891029284\n"
            + "http://www.raagatech.com\n";

    @Override
    public void sendSimpleMail() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void sendEmail(String toEmail, String subject, String body) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void sendGoogleMail(String email, String inquiryname, String followupDetails) throws IOException {
        try {
            MimeMessage msg = javaMailSender.createMimeMessage();
            // true = multipart message
            MimeMessageHelper helper = new MimeMessageHelper(msg);

            helper.setTo(email);
            helper.setBcc("sarvesh.new@gmail.com");
            helper.setSubject("Inquiry: Music Activities");

            String bodyText = "The World Of Music Education & Performance! \n\n"
                    + "Below are the inquiry details.\n\n" + followupDetails
                    + "\n\n Fee Details: \n\n1. For beginner to junior level - Rs. 4500/- quarter. (Option: Rs. 2000/- Month)"
                    + "\n2. For senior upto 6th year level - Rs. 7500/- quarter. (Option: Rs. 3000/- Month)"
                    + "\n\n Please find the registration form attached with this email.";

            boolean html = true;
            helper.setText(bodyText + SIGNATURE, html);
            javaMailSender.send(msg);
        } catch (MessagingException ex) {
            Logger.getLogger(EmailUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void executeCronJob(String subject, String followupDetails) throws UnsupportedEncodingException, IOException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void pushSalesAndServicesViaEmail(String vendorBrandName, String fromEmail, String email, String subject, String offer, String inquiryname) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
