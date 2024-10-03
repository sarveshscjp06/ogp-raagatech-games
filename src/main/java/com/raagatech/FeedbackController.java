/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.raagatech;

import com.raagatech.omp.musicapp.RaagatechMusicDataSource;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author STripathi
 */
@RestController
public class FeedbackController {

    @Autowired
    private RaagatechMusicDataSource musicDataSource;

    @GetMapping("/sharefeedback")
    public String feedbackForm(Model model) {

        return getFeedbackLink();
    }

    @PostMapping("/feedback")
    public String feedbackSubmit(@ModelAttribute final Feedback feedback, Model model) {
        try {
            musicDataSource.addFeedback(feedback.getName(), feedback.getEmail(), feedback.getMobile(),
                    feedback.getFollowupDetails());
        } catch (Exception ex) {
            Logger.getLogger(AppointmentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        String thanks = "\n<h1>Thanks!</h1>\n";
        StringBuilder out = new StringBuilder();
        out.append(thanks);
        out.append(getFeedbackLink());
        return out.toString();
    }

    private String getFeedbackLink() {

        StringBuilder out = new StringBuilder();
        String addFeedbackUrl = "\n<h2><a href='feedback.html'>Click / Tap to add new feedback</a></h2>";
        out.append(addFeedbackUrl);
        // Perform a database operation 
        String feedbackDetails = printFeedbacks();
        out.append(feedbackDetails);
        return out.toString();
    }

    /*
     * Displays feedback details from the raagatech_feedbackdetails table.
     */
    public String printFeedbacks() {

        List<Feedback> feedbackList;
        try {
            feedbackList = musicDataSource.getFeedbacks(7);
        } catch (Exception ex) {
            Logger.getLogger(AppointmentController.class.getName()).log(Level.SEVERE, null, ex);
        }    
        String feedbackDetail;

        feedbackDetail = "\n<h4>Detailed Feedback</h4>";
        feedbackDetail += "\n---------------------";
        int index = 1;
        for (Feedback feedback : feedbackList) {
            feedbackDetail += "\n" + "feedback (" + index + "): " + feedback.toString();
            feedbackDetail += "\n---------------------";
            index++;
        }

        return feedbackDetail;
    }
}
