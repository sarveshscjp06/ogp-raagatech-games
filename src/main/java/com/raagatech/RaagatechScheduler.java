/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.raagatech;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.raagatech.common.datasource.EmailUtilityInterface;

/**
 *
 * @author sarve
 * <seconds> <minutes> <hours> <day-of-month> <month> <day-of-week> <year>
 * year is optional
 */
@Component
public class RaagatechScheduler {

    @Autowired
    private EmailUtilityInterface emailUtility;

    @Scheduled(cron = "0 0 17 ? * 5")
    public void raagatechMusicAd() {
        //every friday 17:00
        SimpleDateFormat dateFormat = new SimpleDateFormat(
            "dd-MM-yyyy HH:mm:ss.SSS");

        String strDate = dateFormat.format(new Date());

        String subject = "raagatech :: learn music";
        String followupDetails = "Greetings!, learn music and get certificate from Prayag Sangit Samiti! Kindly ignore you are already associated woth us!!\n\n";
        emailUtility.executeCronJob(subject, body);
        Logger.getLogger(RaagatechScheduler.class.getName()).log(Level.SEVERE, null, "raagatech Ad job executed at "+strDate);
    }

    @Scheduled(cron = "0 0 6 15 8 ?")
    public void independenceDayJob() {
        //15 of august 06:00
        SimpleDateFormat dateFormat = new SimpleDateFormat(
            "dd-MM-yyyy HH:mm:ss.SSS");

        String strDate = dateFormat.format(new Date());

        String subject = "raagatech :: Happy Independence Day";
        String followupDetails = "Greetings!, Our best wishes to you and your family on occassion of Independence Day.\n\n";
        emailUtility.executeCronJob(subject, body);
        Logger.getLogger(RaagatechScheduler.class.getName()).log(Level.SEVERE, null, "raagatech Independence Day job executed at "+strDate);
    }

    @Scheduled(cron = "0 0 6 2 10 ?")
    public void gandhiJayantiJob() {
        //2 of october 06:00
        SimpleDateFormat dateFormat = new SimpleDateFormat(
            "dd-MM-yyyy HH:mm:ss.SSS");

        String strDate = dateFormat.format(new Date());

        String subject = "raagatech :: Happy Gandhi Jayanti";
        String followupDetails = "Greetings!, Our best wishes to you and your family on occassion of Gandhi Jayanti.\n\n";
        emailUtility.executeCronJob(subject, body);
        Logger.getLogger(RaagatechScheduler.class.getName()).log(Level.SEVERE, null, "raagatech Gandhi Jayanti job executed at "+strDate);
    }

    @Scheduled(cron = "0 0 6 25 12 ?")
    public void christmasDayJob() {
        //25 of december 06:00
        SimpleDateFormat dateFormat = new SimpleDateFormat(
            "dd-MM-yyyy HH:mm:ss.SSS");

        String strDate = dateFormat.format(new Date());

        String subject = "raagatech :: Merry Christmas";
        String followupDetails = "Greetings!, Our best wishes to you and your family on occassion of Christmas.\n\n";
        emailUtility.executeCronJob(subject, body);
        Logger.getLogger(RaagatechScheduler.class.getName()).log(Level.SEVERE, null, "raagatech Christmas job executed at "+strDate);
    }

    @Scheduled(cron = "0 0 6 1 1 ?")
    public void newYearJob() {
        //1 of january 06:00
        SimpleDateFormat dateFormat = new SimpleDateFormat(
            "dd-MM-yyyy HH:mm:ss.SSS");

        String strDate = dateFormat.format(new Date());

        String subject = "raagatech :: Happy New Year";
        String followupDetails = "Greetings!, Our best wishes to you and your family on occassion of NEW YEAR.\n\n";
        emailUtility.executeCronJob(subject, body);
        Logger.getLogger(RaagatechScheduler.class.getName()).log(Level.SEVERE, null, "raagatech happy new year job executed at "+strDate);
    }

    @Scheduled(cron = "0 0 6 26 1 ?")
    public void republicDayJob() {
        //26 of january 06:00
        SimpleDateFormat dateFormat = new SimpleDateFormat(
            "dd-MM-yyyy HH:mm:ss.SSS");

        String strDate = dateFormat.format(new Date());

        String subject = "raagatech :: Happy Republic Day";
        String followupDetails = "Greetings!, Our best wishes to you and your family on occassion of REPUBLIC DAY.\n\n";
        emailUtility.executeCronJob(subject, body);
        Logger.getLogger(RaagatechScheduler.class.getName()).log(Level.SEVERE, null, "raagatech REPUBLIC DAY job executed at "+strDate);
    }

}