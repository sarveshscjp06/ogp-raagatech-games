/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.raagatech.crm.customerapp;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author sarve
 */
@RestController
@RequestMapping("/resources/customer")
public class SamcrmCustomerApplication {
    @RequestMapping
    public String home() {
        return "<h1>Spring Boot Hello World!</h1><br/> This service is about SAMCRM Customer Application";
    }
}
