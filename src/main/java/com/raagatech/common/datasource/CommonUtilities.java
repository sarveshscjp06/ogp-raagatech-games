/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.raagatech.common.datasource;

import com.raagatech.omp.musicapp.InquiryBean;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

/**
 *
 * @author sarve
 */
@Service
public class CommonUtilities implements CommonUtilitiesInterface {

    @Override
    public boolean isNotNull(String text) {
        return text != null && text.trim().length() > 0 ? Boolean.TRUE : Boolean.FALSE;
    }

    @Override
    public String constructJSON(String tag, boolean status) {
        JSONObject object = new JSONObject();
        try {
            object.put("tag", tag);
            object.put("status", status);
        } catch (JSONException jsone) {
        }
        return object.toString();
    }

    @Override
    public String constructJSON(String tag, boolean status, String errorMessage) {
        JSONObject object = new JSONObject();
        try {
            object.put("tag", tag);
            object.put("status", status);
            object.put("errorMessage", errorMessage);
        } catch (JSONException jsone) {
        }
        return object.toString();
    }

    @Override
    public String constructJSON(String tag, LinkedHashMap<Integer, String> map) {
        JSONObject object = new JSONObject();
        try {
            object.put("tag", tag);
            object.put("status", map);
        } catch (JSONException jsone) {
        }
        return object.toString();
    }

    @Override
    public String constructJSON(String tag, ArrayList<InquiryBean> inquiryList) {
        JSONObject object = new JSONObject();
        try {
            object.put("tag", tag);
            object.put("status", inquiryList);
        } catch (JSONException jsone) {
        }
        return object.toString();
    }
}
