/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.raagatech.crm.customerapp;

import com.raagatech.common.datasource.CommonUtilitiesInterface;
import com.raagatech.common.datasource.EmailUtilityInterface;
import com.raagatech.bean.InquiryBean;
import com.sam.raagatech.ogp.samcrm.contacts.UserDataSource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author sarve
 */
@RestController
@RequestMapping("/resources/register")
public class SamcrmCustomerApplication {

    @Autowired
    private UserDataSource userDataSource;
    @Autowired
    private CommonUtilitiesInterface commonUtilities;
    @Autowired
    private EmailUtilityInterface emailUtility;

    @RequestMapping
    public String home() {
        return "<h1>Spring Boot Hello World!</h1><br/> This service is about SAMCRM Customer Application";
    }

    @RequestMapping(value = "/doregister", method = RequestMethod.GET)
    public String doRegister(@RequestParam("username") String username, @RequestParam("password") String password,
            @RequestParam("email") String email, @RequestParam("mobile") String mobileNo) {
        String response;
        int result = registerUser(username, password, email, Long.valueOf(mobileNo));
        if (result == 0) {
            response = commonUtilities.constructJSON("register", true);
        } else {
            response = commonUtilities.constructJSON("register", false, "sql insertion error occurred");
        }
        return response;
    }

    private int registerUser(String username, String password, String email, long mobileNo) {

        int result = 3;
        if (commonUtilities.isNotNull(username) && commonUtilities.isNotNull(password) && commonUtilities.isNotNull(email)) {
            try {
                if (userDataSource.insertUser(username, password, email, mobileNo)) {
                    result = 0;
                }
            } catch (Exception e) {
            }
        }
        return result;
    }

    @RequestMapping(value = "/doregisterinquiry", method = RequestMethod.GET)
    public String doRegisterInquiry(@RequestParam("inquiryname") String inquiryname, @RequestParam("inspirationid") String inspirationid,
            @RequestParam("email") String email, @RequestParam("mobile") String mobileNo, @RequestParam("levelid") String levelid,
            @RequestParam("address") String address, @RequestParam("followupDetails") String followupDetails) {
        String response;
        int result = registerInquiry(inquiryname, Integer.valueOf(inspirationid), email, Long.valueOf(mobileNo), Integer.valueOf(levelid),
                address, followupDetails);
        if (result == 0) {
            response = commonUtilities.constructJSON("register", true);
        } else {
            response = commonUtilities.constructJSON("register", false, "sql insertion error occurred");
        }
        return response;
    }

    private int registerInquiry(String inquiryname, int inspirationid, String email, long mobileNo,
            int levelid, String address, String followupDetails) {

        int result = 3;
        if (commonUtilities.isNotNull(inquiryname) && commonUtilities.isNotNull(email)) {
            try {
                if (userDataSource.insertInquiry(inquiryname, inspirationid, email, mobileNo,
                        levelid, address, followupDetails, "", "", "", "", 0, null,
                        "", "", "", "")) {
                    result = 0;
                    if (!email.equalsIgnoreCase("raksha@raagatech.com")) {
                        emailUtility.sendGoogleMail(email, inquiryname, followupDetails);
                    }
                    //SMSUtils.sendSMS(String.valueOf(mobileNo), followupDetails);
                }
            } catch (IOException e) {
            } catch (Exception ex) {
                Logger.getLogger(SamcrmCustomerApplication.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return result;
    }

    @RequestMapping(value = "/doselectlevel", method = RequestMethod.GET)
    public String doSelectLevel() throws Exception {
        String response;
        LinkedHashMap<Integer, String> levelMap = userDataSource.selectLevel();
        if (!levelMap.isEmpty()) {
            response = commonUtilities.constructJSON("selectlevel", levelMap);
        } else {
            response = commonUtilities.constructJSON("selectlevel", false, "no data available");
        }
        return response;
    }

    @RequestMapping(value = "/doselectinspiration", method = RequestMethod.GET)
    public String doSelectInspiration() throws Exception {
        String response;
        LinkedHashMap<Integer, String> inspirationMap = userDataSource.selectInspiration();
        if (!inspirationMap.isEmpty()) {
            response = commonUtilities.constructJSON("selectinspiration", inspirationMap);
        } else {
            response = commonUtilities.constructJSON("selectinspiration", false, "no data available");
        }
        return response;
    }

    @RequestMapping(value = "/dolistinquiry", method = RequestMethod.GET)
    public String doListInquiry() throws Exception {
        String response;
        ArrayList<InquiryBean> inquiryList = userDataSource.listInquiry();
        if (!inquiryList.isEmpty()) {
            response = commonUtilities.constructJSON("listinquiry", inquiryList);
        } else {
            response = commonUtilities.constructJSON("listinquiry", false, "no data available");
        }
        return response;
    }

    @RequestMapping(value = "/doupdateinquiry", method = RequestMethod.GET)
    public String doUpdateInquiry(@RequestParam("inquiry_id") String inquiry_id, @RequestParam("inquiryname") String inquiryname, @RequestParam("inspirationid") String inspirationid,
            @RequestParam("email") String email, @RequestParam("mobile") String mobileNo, @RequestParam("levelid") String levelid,
            @RequestParam("address") String address, @RequestParam("followupDetails") String followupDetails) throws Exception {
        String response;
        int result = updateInquiry(Integer.valueOf(inquiry_id), inquiryname, Integer.valueOf(inspirationid), email, Long.valueOf(mobileNo), Integer.valueOf(levelid),
                address, followupDetails);
        if (result == 0) {
            response = commonUtilities.constructJSON("register", true);
        } else {
            response = commonUtilities.constructJSON("register", false, "sql insertion error occurred");
        }
        return response;
    }

    private int updateInquiry(int inquiry_id, String inquiryname, int inspirationid, String email, long mobileNo,
            int levelid, String address, String followupDetails) throws Exception {

        int result = 3;
        if (inquiry_id > 0 && commonUtilities.isNotNull(inquiryname) && commonUtilities.isNotNull(email)) {
            try {
                if (userDataSource.updateInquiry(inquiry_id, inquiryname, inspirationid, email, mobileNo,
                        levelid, address, followupDetails, "", "", "", "", 0, null,
                        "", "", "", "")) {
                    result = 0;
                }
                if (!email.equalsIgnoreCase("raksha@raagatech.com")) {
                    emailUtility.sendGoogleMail(email, inquiryname, followupDetails);
                }
                //SMSUtils.sendSMS(String.valueOf(mobileNo), followupDetails);

            } catch (IOException e) {
            }
        }
        return result;
    }

    @RequestMapping(value = "/doselectinquirystatus", method = RequestMethod.GET)
    public String doSelectInquiryStatus() throws Exception {
        String response;
        LinkedHashMap<Integer, String> inquiryStatusMap = userDataSource.selectInquiryStatus();
        if (!inquiryStatusMap.isEmpty()) {
            response = commonUtilities.constructJSON("selectlevel", inquiryStatusMap);
        } else {
            response = commonUtilities.constructJSON("selectlevel", false, "no data available");
        }
        return response;
    }

    @RequestMapping(value = "/doupdatefollowup", method = RequestMethod.GET)
    public String doUpdateFollowup(@RequestParam("inquiry_id") String inquiry_id, @RequestParam("inquirystatus_id") String inquirystatus_id,
            @RequestParam("followupDetails") String followupDetails) throws Exception {
        String response;
        int result = updateFollowup(Integer.valueOf(inquiry_id), Integer.valueOf(inquirystatus_id), followupDetails);
        if (result == 0) {
            response = commonUtilities.constructJSON("register", true);
        } else {
            response = commonUtilities.constructJSON("register", false, "sql insertion error occurred");
        }
        return response;
    }

    private int updateFollowup(int inquiry_id, int inquirystatus_id, String followupDetails) throws Exception {

        int result = 3;
        if (inquiry_id > 0 && commonUtilities.isNotNull(followupDetails) && inquirystatus_id > 0) {
            if (userDataSource.updateFollowup(inquiry_id, inquirystatus_id, followupDetails)) {
                result = 0;
            }
            InquiryBean inquiryBean = userDataSource.getInquiryById(inquiry_id);
            if (inquiryBean != null) {
                if (!inquiryBean.getEmail().equalsIgnoreCase("raksha@raagatech.com")) {
                    emailUtility.sendGoogleMail(inquiryBean.getEmail(), inquiryBean.getFirstname(), followupDetails);
                }
                //SMSUtils.sendSMS(String.valueOf(inquiryBean.getMobile()), followupDetails);
            }
        }
        return result;
    }
}
