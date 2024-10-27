/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.raagatech.common.datasource;

import com.raagatech.bean.OrderDataBean;
import com.raagatech.bean.OrderTrackerBean;
import com.raagatech.bean.ProductCategoryBean;
import com.raagatech.bean.ProductsAndServicesBean;
import com.raagatech.bean.SliderImageBean;
import com.raagatech.bean.VendorDataBean;
import com.raagatech.bean.InquiryBean;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import org.json.JSONArray;
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
    
    /**
     *
     * @param tag
     * @param status
     * @param contactList
     * @return
     */
    @Override
    public String constructContactJSON(String tag, boolean status, ArrayList<VendorDataBean> contactList) {

        JSONObject object = new JSONObject();
        try {
            object.put("tag", contactList);
            object.put("status", status);
            object.put("data", tag);
        } catch (JSONException jsone) {
        }
        return object.toString();
    }

    @Override
    public String constructProductCategoryJSON(ArrayList<ProductCategoryBean> vendorList, boolean status, String errorMessage) {

        JSONObject object = new JSONObject();
        try {
            object.put("tag", vendorList);
            object.put("status", status);
            object.put("errorMessage", errorMessage);
        } catch (JSONException jsone) {
        }
        return object.toString();
    }

    @Override
    public String constructProductItemsJSON(ArrayList<ProductsAndServicesBean> vendorList, boolean status, String errorMessage) {

        JSONObject object = new JSONObject();
        try {
            object.put("tag", vendorList);
            object.put("status", status);
            object.put("errorMessage", errorMessage);
        } catch (JSONException jsone) {
        }
        return object.toString();
    }
    
    @Override
    public String constructJSON_ForSliderImage(String tag, ArrayList<SliderImageBean> sliderImageList) {

        JSONArray object = new JSONArray();
        try {
            //object.put("tag", tag);
            object.put(sliderImageList);
        } catch (JSONException jsone) {
        }
        return object.toString();
    }
    
    @Override
    public String constructOrdersJSON(ArrayList<OrderDataBean> ordersList, LinkedHashMap<String, String> map, boolean isError) {

        JSONObject object = new JSONObject();
        try {
            object.put("tag", ordersList);
            object.put("status", map);
            object.put("errorMessage", isError);
        } catch (JSONException jsone) {
        }
        return object.toString();
    }

    @Override
    public String constructOrdersJSON(OrderDataBean orderDataBean, LinkedHashMap<String, String> map, boolean isError) {

        JSONObject object = new JSONObject();
        try {
            object.put("tag", orderDataBean);
            object.put("status", map);
            object.put("errorMessage", isError);
        } catch (JSONException jsone) {
        }
        return object.toString();
    }
    
    @Override
    public String constructJSONString(ArrayList<OrderTrackerBean> list, boolean isError, String msg) {

        JSONObject object = new JSONObject();
        try {
            object.put("tag", list);
            object.put("status", isError);
            object.put("errorMessage", msg);
        } catch (JSONException jsone) {
        }
        return object.toString();
    }
    
    @Override
    public String constructJSON(ArrayList<VendorDataBean> vendorList, boolean status, LinkedHashMap<String, String> individualData) {

        JSONObject object = new JSONObject();
        try {
            object.put("tag", vendorList);
            object.put("status", status);
            object.put("errorMessage", individualData);
        } catch (JSONException jsone) {
        }
        return object.toString();
    }
    
    @Override
    public String constructJSONString(ArrayList<VendorDataBean> vendorList, LinkedHashMap<String, String> map, boolean isError) {

        JSONObject object = new JSONObject();
        try {
            object.put("tag", vendorList);
            object.put("status", map);
            object.put("errorMessage", isError);
        } catch (JSONException jsone) {
        }
        return object.toString();
    }
}
