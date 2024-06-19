/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.raagatech.common.datasource;

import com.raagatech.omp.musicapp.InquiryBean;
import com.raagatech.omp.musicapp.RaagatechMusicDataSource;
import com.raagatech.omp.musicapp.UserDataBean;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import javax.mail.Message;
import javax.mail.internet.InternetAddress;

/**
 * https://www.codejava.net/frameworks/spring-boot/email-sending-tutorial
    * https://mkyong.com/spring-boot/spring-boot-how-to-send-email-via-smtp/
 * @author sarve
 */
@Service
public class EmailUtils implements EmailUtilityInterface {

    @Autowired
    private RaagatechMusicDataSource musicDataSource;
    @Autowired
    private JavaMailSender javaMailSender;

    private static final String SIGNATURE = "<h4>Raksha Tripathi</h4>"
            .concat("<h5><p>'PRAVEEN' Indian Classical Vocal & Instrumental</p>")
            .concat("<p>Principal / Superintendent</p>")
            .concat("<p>raagatech 'The World of Music Education & Performance'</p>")
            .concat("<p>(An authorized exam & study centre for PRAYAG SANGIT SAMITI, Prayagraj)</p>")
            .concat("<p>Land-Line: +91 120 4276874 Mobile: +919891029284</p>")
            .concat("<p>http://www.raagatech.com</p></h5>");

    @Override
    public void sendSimpleMail() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void sendEmail(String toEmail, String subject, String body) {
        try {
            MimeMessage msg = javaMailSender.createMimeMessage();
            // true = multipart message
            MimeMessageHelper helper = new MimeMessageHelper(msg);

            helper.setTo(toEmail);
            helper.setBcc("sarvesh.new@gmail.com");
            helper.setSubject(subject);

            String bodyText = "<h1>The World Of Music Education & Performance!</h1>"
                    .concat(body)
                    .concat(SIGNATURE);

            boolean html = true;
            helper.setText(bodyText, html);
            javaMailSender.send(msg);
        } catch (MessagingException ex) {
            Logger.getLogger(EmailUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            List<UserDataBean> contactList = musicDataSource.listOverAllContacts();
            int contactSize = contactList.size();
            if (contactSize > 75) {
                contactSize = 75;
            }
            var addresses = new InternetAddress[contactSize];
            int index = 0;
            for (UserDataBean contact : contactList) {
                if (addresses.length == 75) {
                    break;
                }
                addresses[index] = new InternetAddress(contact.getEmail(), contact.getUserName());
                index++;
            }
            if (addresses.length != 0) {
                sendDailyEMailAd(subject, addresses, followupDetails);
            }
        } catch (Exception ex) {
            Logger.getLogger(EmailUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void pushSalesAndServicesViaEmail(String vendorBrandName, String fromEmail, String email, String subject, String offer, String inquiryname) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private void sendDailyEMailAd(String subject, InternetAddress[] addresses, String body) throws IOException {
        
        try {
            MimeMessage msg = javaMailSender.createMimeMessage();
            msg.addRecipients(Message.RecipientType.TO, addresses);
            // true = multipart message
            MimeMessageHelper helper = new MimeMessageHelper(msg);
            helper.setBcc("sarvesh.new@gmail.com");
            msg.setSubject(subject);

            String bodyText = "<h1>The World Of Music Education & Performance!</h1>"
                    .concat(body)
                    .concat(SIGNATURE);

            boolean html = true;
            helper.setText(bodyText, html);
            javaMailSender.send(msg);
        } catch (MessagingException ex) {
            Logger.getLogger(EmailUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
