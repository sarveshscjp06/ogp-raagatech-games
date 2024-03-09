package com.raagatech;

import com.raagatech.common.datasource.EmailUtilityInterface;
import com.raagatech.omp.musicapp.RaagatechMusicDataSource;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AppointmentController {

    @Autowired
    private RaagatechMusicDataSource musicDataSource;
    @Autowired
    private EmailUtilityInterface emailUtility;

    @GetMapping("/appointment")
    public String appointmentForm(Model model) {

        model.addAttribute("appointment", new Appointment());
        return "appointment";
    }

    @PostMapping("/appointment")
    public String appointmentSubmit(@ModelAttribute Appointment appointment, Model model) {

        boolean appointmentStatus = Boolean.FALSE;
        try {
            appointmentStatus = musicDataSource.insertInquiry(appointment.getName(), 1, appointment.getEmail(), appointment.getPhone(), 1,
                    "appointment", appointment.getFollowupDetails(), "091", "", "", "", 0, "",
                    "", "", "", "", 1);
        } catch (Exception ex) {
            Logger.getLogger(AppointmentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (appointmentStatus) {
            String body = "Hi ".concat(appointment.getName()).concat(", ")
                    .concat("The appointment details have been sent via SMS and e-mail service.")
                    .concat("Thanks!");
            emailUtility.sendEmail(appointment.getEmail(), "raagatech: inquiry registration", body);
            appointment.setContent(body);
        }
        model.addAttribute("appointment", appointment);

        return "result";
    }

}
