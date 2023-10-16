/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.omp.raagatech.musicapp;

import com.admin.raagatech.datasource.CommonUtilitiesInterface;
import com.admin.raagatech.datasource.EmailUtilityInterface;
import com.admin.raagatech.datasource.OracleDatabaseInterface;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Online raagatech music application services
 *
 * @author sarve
 */
@RestController
@RequestMapping("/resources/register")
public class RaagatechMusicAppServices {

    @Autowired
    private OracleDatabaseInterface dataSource;
    @Autowired
    private CommonUtilitiesInterface commonUtilities;
    @Autowired
    private EmailUtilityInterface emailUtility;

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
                if (dataSource.insertUser(username, password, email, mobileNo)) {
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
                if (dataSource.insertInquiry(inquiryname, inspirationid, email, mobileNo,
                        levelid, address, followupDetails, "", "", "", "", 0, null,
                        "", "", "", "")) {
                    result = 0;
                    if (!email.equalsIgnoreCase("raksha@raagatech.com")) {
                        emailUtility.sendGoogleMail(email, inquiryname, followupDetails);
                    }
//                    SMSUtils.sendSMS(String.valueOf(mobileNo), followupDetails);
                }
            } catch (Exception e) {
            }
        }
        return result;
    }

    @RequestMapping(value = "/doselectlevel", method = RequestMethod.GET)
    public String doSelectLevel() throws Exception {
        String response;
        LinkedHashMap<Integer, String> levelMap = dataSource.selectLevel();
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
        LinkedHashMap<Integer, String> inspirationMap = dataSource.selectInspiration();
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
        ArrayList<InquiryBean> inquiryList = dataSource.listInquiry();
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
            @RequestParam("address") String address, @RequestParam("followupDetails") String followupDetails) {
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
            int levelid, String address, String followupDetails) {

        int result = 3;
        if (inquiry_id > 0 && commonUtilities.isNotNull(inquiryname) && commonUtilities.isNotNull(email)) {
            try {
                if (dataSource.updateInquiry(inquiry_id, inquiryname, inspirationid, email, mobileNo,
                        levelid, address, followupDetails, "", "", "", "", 0, null,
                        "", "", "", "")) {
                    result = 0;
                }
                if (!email.equalsIgnoreCase("raksha@raagatech.com")) {
                    emailUtility.sendGoogleMail(email, inquiryname, followupDetails);
                }
//                SMSUtils.sendSMS(String.valueOf(mobileNo), followupDetails);

            } catch (Exception e) {
            }
        }
        return result;
    }

    @RequestMapping(value = "/doselectinquirystatus", method = RequestMethod.GET)
    public String doSelectInquiryStatus() throws Exception {
        String response;
        LinkedHashMap<Integer, String> inquiryStatusMap = dataSource.selectInquiryStatus();
        if (!inquiryStatusMap.isEmpty()) {
            response = commonUtilities.constructJSON("selectlevel", inquiryStatusMap);
        } else {
            response = commonUtilities.constructJSON("selectlevel", false, "no data available");
        }
        return response;
    }

    @RequestMapping(value = "/doupdatefollowup", method = RequestMethod.GET)
    public String doUpdateFollowup(@RequestParam("inquiry_id") String inquiry_id, @RequestParam("inquirystatus_id") String inquirystatus_id,
            @RequestParam("followupDetails") String followupDetails) {
        String response;
        int result = updateFollowup(Integer.valueOf(inquiry_id), Integer.valueOf(inquirystatus_id), followupDetails);
        if (result == 0) {
            response = commonUtilities.constructJSON("register", true);
        } else {
            response = commonUtilities.constructJSON("register", false, "sql insertion error occurred");
        }
        return response;
    }

    private int updateFollowup(int inquiry_id, int inquirystatus_id, String followupDetails) {

        int result = 3;
        if (inquiry_id > 0 && commonUtilities.isNotNull(followupDetails) && inquirystatus_id > 0) {
            try {
                if (dataSource.updateFollowup(inquiry_id, inquirystatus_id, followupDetails)) {
                    result = 0;
                }

                InquiryBean inquiryBean = dataSource.getInquiryById(inquiry_id);
                if (inquiryBean != null) {
                    if (!inquiryBean.getEmail().equalsIgnoreCase("raksha@raagatech.com")) {
                        emailUtility.sendGoogleMail(inquiryBean.getEmail(), inquiryBean.getFirstname(), followupDetails);
                    }
//                    SMSUtils.sendSMS(String.valueOf(inquiryBean.getMobile()), followupDetails);
                }
            } catch (Exception e) {
            }
        }
        return result;
    }
}
