/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.raagatech;

import com.raagatech.common.datasource.EmailUtilityInterface;
import com.raagatech.omp.musicapp.RaagatechMusicDataSource;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author sarve
 */
@Controller
public class RegistrationFormController {

    @Autowired
    private RaagatechMusicDataSource musicDataSource;
    @Autowired
    private EmailUtilityInterface emailUtility;

    @PostMapping("/registrationForm")
    public String registrationFormSubmit(@Valid @ModelAttribute RegistrationForm registration, Model model) {

        boolean registrationFormStatus = Boolean.FALSE;
        try {
            registrationFormStatus = musicDataSource.insertInquiry(registration.getFirstname(), 1, registration.getEmail(), registration.getMobile(),
                    0, registration.getAddress_line1(), registration.getFollowup_details(), "091",
                    registration.getDate_of_birth(), 0, "", "" + registration.getGender(),
                    1, registration.getComfortability(), registration.getPrimaryskill(), 1, 201009,
                    "6", registration.getFather_name(), registration.getMother_name(), 0);
        } catch (Exception ex) {
            Logger.getLogger(RegistrationFormController.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (registrationFormStatus) {
            String body = "Hi ".concat(registration.getFirstname()).concat(", ")
                    .concat("The registration details have been sent via SMS and e-mail service.")
                    .concat("Thanks!");
            emailUtility.sendEmail(registration.getEmail(), "raagatech: registration", body);
        }

        return "redirect:html/thanks.html";
    }
}
