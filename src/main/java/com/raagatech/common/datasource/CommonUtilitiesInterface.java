/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.raagatech.common.datasource;

import com.raagatech.bean.OrderDataBean;
import com.raagatech.bean.OrderTrackerBean;
import com.raagatech.bean.ProductCategoryBean;
import com.raagatech.bean.ProductsAndServicesBean;
import com.raagatech.bean.SliderImageBean;
import com.raagatech.bean.VendorDataBean;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import com.raagatech.bean.InquiryBean;

/**
 *
 * @author sarve
 */
public interface CommonUtilitiesInterface {

    public boolean isNotNull(String text);

    public String constructJSON(String tag, boolean status);

    public String constructJSON(String tag, boolean status, String errorMessage);

    public String constructJSON(String tag, LinkedHashMap<Integer, String> map);

    //public String constructJSON(String tag, ArrayList<InquiryBean> inquiryList);
    
    public String constructJSON(String tag, ArrayList<InquiryBean> inquiryList);

    public String constructContactJSON(String tag, boolean status, ArrayList<VendorDataBean> contactList);

    public String constructProductCategoryJSON(ArrayList<ProductCategoryBean> vendorList, boolean status, String errorMessage);

    public String constructProductItemsJSON(ArrayList<ProductsAndServicesBean> vendorList, boolean status, String errorMessage);

    public String constructJSON_ForSliderImage(String tag, ArrayList<SliderImageBean> sliderImageList);
    
    public String constructOrdersJSON(ArrayList<OrderDataBean> ordersList, LinkedHashMap<String, String> map, boolean isError);
    
    public String constructOrdersJSON(OrderDataBean orderDataBean, LinkedHashMap<String, String> map, boolean isError);
    
    public String constructJSONString(ArrayList<OrderTrackerBean> list, boolean isError, String msg);
    
    public String constructJSON(ArrayList<VendorDataBean> vendorList, boolean status, LinkedHashMap<String, String> individualData);
    
    public String constructJSONString(ArrayList<VendorDataBean> vendorList, LinkedHashMap<String, String> map, boolean isError);
}
