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
@Controller
public class FeedbackController {

    @Autowired
    private RaagatechMusicDataSource musicDataSource;

    @PostMapping("/feedback")
    public String feedbackSubmit(@ModelAttribute final Feedback feedback, Model model) {
        try {
            musicDataSource.addFeedback(feedback.getName(), feedback.getEmail(), feedback.getMobile(),
                    feedback.getFollowupDetails());
        } catch (Exception ex) {
            Logger.getLogger(AppointmentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "redirect:html/thanks.html";
    }
}
