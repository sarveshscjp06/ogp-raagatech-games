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
    private Integer totalFormCount = 0;
    private Integer subjectwiseFormCount = 0;
    private List<Integer> classwiseTotalForms = new ArrayList<>();
    
    private Integer totalFees = 0;
    private Integer subjectwiseTotalFees = 0;
    private List<Integer> classwiseTotalFees = new ArrayList<>();
    
    private Integer totalFeesCollectedCount = 0;
    private Integer subjectwiseFeesCollectedCount = 0;
    private List<Integer> classwiseFeesCollectedCount = new ArrayList<>();

    private Integer totalResultReceived = 0;
    private Integer subjectwiseResultReceived = 0;
    private List<Integer> classwiseResultReceived = new ArrayList<>();

    private Integer totalResultPending = 0;
    private Integer subjectwiseResultPending = 0;
    private List<Integer> classwiseResultPending = new ArrayList<>();
    
}