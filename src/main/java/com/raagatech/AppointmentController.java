package com.raagatech;

import com.raagatech.common.datasource.EmailUtilityInterface;
import com.raagatech.omp.musicapp.RaagatechMusicDataSource;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AppointmentController {

    @Autowired
    private RaagatechMusicDataSource musicDataSource;
    @Autowired
    private EmailUtilityInterface emailUtility;

    @PostMapping("/appointment")
    public String appointmentSubmit(@ModelAttribute Appointment appointment, Model model) {

        boolean appointmentStatus = Boolean.FALSE;
        try {
            if (!appointment.getName().isEmpty() && !appointment.getEmail().isEmpty() && String.valueOf(appointment.getPhone()).length() == 10
                    && !appointment.getFollowupDetails().isEmpty()) {
                appointmentStatus = musicDataSource.addFollowUps(appointment.getName(), appointment.getEmail(), appointment.getPhone(),
                        appointment.getFollowupDetails(), 8);
            }
        } catch (Exception ex) {
            Logger.getLogger(AppointmentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (appointmentStatus) {
            String body = "Hi ".concat(appointment.getName()).concat(", ")
                    .concat("The appointment details have been sent via SMS and e-mail service.")
                    .concat("Thanks!");
            emailUtility.sendEmail(appointment.getEmail(), "raagatech: Appointment", body);
            appointment.setContent(body);
        }
        
        return "redirect:html/thanks.html";
    }

}
