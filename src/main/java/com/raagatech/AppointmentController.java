package com.raagatech;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AppointmentController {

    @GetMapping("/appointment")
    public String appointmentForm(Model model){

        model.addAttribute("appointment", new Appointment());
        return "appointment";
    }

    @PostMapping("/appointment")
    public String appointmentSubmit(@ModelAttribute Appointment appointment, Model model){

        model.addAttribute("appointment", appointment);
        return "result";
    }

}