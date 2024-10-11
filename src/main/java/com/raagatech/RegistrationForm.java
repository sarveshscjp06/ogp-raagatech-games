/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.raagatech;

import java.util.Date;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author sarve
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegistrationForm {
    private Integer registrationId;
    @NotEmpty
    private String firstname;
    private Date inquiry_date;
    private Integer inspiration_id;
    @NotEmpty
    private String email;
    @Size(min=10, message="Not Valid")
    private Long mobile;
    private Integer level_id;
    private String address_line1;
    @NotEmpty
    private String followup_details;
    private Integer inquirystatus_id;
    private String label_text;
    private String label_color;
    private String nationality;
    private String father_name;
    @NotEmpty
    private String mother_name;
    @NotEmpty
    private String date_of_birth;
    private Long telephone;
    private String photo;
    @NotEmpty
    private char gender;
    private String inspiration;
    private String comfortability;
    @NotEmpty
    private String primaryskill;
    private Integer pincode;
    private String examSession;
    private int emailVerified;
    private int mobileVerified;
    private int examFees;
    private int feesPaidStatus;
    private int inspiratorId;
}
