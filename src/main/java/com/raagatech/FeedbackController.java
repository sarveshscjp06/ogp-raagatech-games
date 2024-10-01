/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo;

import com.raagatech.omp.musicapp.RaagatechMusicDataSource;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/feedback")
    public String feedbackForm(Model model){
        
        //model.addAttribute("greeting", new Greeting());
        return "feedback";
    }
    
    @PostMapping("/feedback")
    public String feedbackSubmit(@ModelAttribute Feedback feedback, Model model) {
        boolean appointmentStatus = Boolean.FALSE;
        try {
            appointmentStatus = musicDataSource.insertInquiry(appointment.getName(), 1, appointment.getEmail(), appointment.getPhone(), 1,
                    "appointment", appointment.getFollowupDetails(), "091", "", 0, "",
                    "", 0, "", "", 1, 201009, "", "", "", 0);
        } catch (Exception ex) {
            Logger.getLogger(AppointmentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("feedback: "+feedback.toString());
//        model.addAttribute("greeting", greeting);
        return "thanks";
    }
}
