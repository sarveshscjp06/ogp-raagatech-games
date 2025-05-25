/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.raagatech.omp.musicapp;

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
public class PssExamReportBean {

    private Integer educatorId;
    private Integer subjectId;
    private Integer yearId;
    private Integer totalForms;
    private Integer totalFees;
    private Integer totalPssFees;
    private Integer totalFeesCollectedCount;
    private Integer totalDiscount;
    private Integer discount;
}
