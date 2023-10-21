/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.raagatech.common.datasource;

import com.raagatech.omp.musicapp.InquiryBean;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 *
 * @author sarve
 */
public interface CommonUtilitiesInterface {

    public boolean isNotNull(String text);

    public String constructJSON(String tag, boolean status);

    public String constructJSON(String tag, boolean status, String errorMessage);

    public String constructJSON(String tag, LinkedHashMap<Integer, String> map);

    public String constructJSON(String tag, ArrayList<InquiryBean> inquiryList);
}
