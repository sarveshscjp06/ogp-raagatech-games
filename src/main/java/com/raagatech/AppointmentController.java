package com.raagatech;

import org.springframework.stereotype.Controller;

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