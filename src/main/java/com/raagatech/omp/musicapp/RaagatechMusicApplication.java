/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.raagatech.omp.musicapp;

import com.raagatech.common.datasource.CommonUtilitiesInterface;
import com.raagatech.common.datasource.EmailUtilityInterface;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
@RequestMapping("/resources/music")
public class RaagatechMusicApplication {

    @Autowired
    private RaagatechMusicDataSource musicDataSource;
    @Autowired
    private CommonUtilitiesInterface commonUtilities;
    @Autowired
    private EmailUtilityInterface emailUtility;

    @RequestMapping
    public String home() {
        return "<h1>Spring Boot Hello World!</h1><br/> This service is about Raagatech Music Application";
    }

    @RequestMapping(value = "/dologin", method = RequestMethod.GET)
    public String doLogin(@RequestParam("userName") String userName, @RequestParam("password") String password) {
        String response = null;
        if (commonUtilities.isNotNull(userName) && commonUtilities.isNotNull(password)) {
            try {
                UserDataBean userData = musicDataSource.getUserData(userName, password);
                if (userData != null) {
                    List<UserDataBean> userDataList = new ArrayList<>();
                    userDataList.add(userData);
                    JSONArray jsonArray = new JSONArray(userDataList);
                    response = jsonArray.toString();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return response;
    }

    @RequestMapping(value = "/dochangepassword", method = RequestMethod.GET)
    public String doChangePassword(@RequestParam("regName") String username, @RequestParam("regPassword") String password,
            @RequestParam("regMobile") String mobileNo, @RequestParam("regGender") String gender,
            @RequestParam("regPostalAddress") String postalAddress, @RequestParam("regPincode") String pincode, @RequestParam("userId") int userId) {
        String response = "false";
        if (commonUtilities.isNotNull(username) && commonUtilities.isNotNull(password)) {
            try {
                musicDataSource.updateUserData(username, password, Long.valueOf(mobileNo),
                        gender, postalAddress, pincode, userId);
                response = "true";
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return response;
    }

    @RequestMapping(value = "/doregister", method = RequestMethod.POST)
    public String doRegister(@RequestParam("regName") String username, @RequestParam("regPassword") String password,
            @RequestParam("regEmail") String email, @RequestParam("regMobile") String mobileNo, @RequestParam("regGender") String gender,
            @RequestParam("regPostalAddress") String postalAddress, @RequestParam("regPincode") String pincode) {

        String response = "false";
        try {
            if (musicDataSource.insertUser(username, password, email, Long.valueOf(mobileNo), gender, postalAddress, pincode)) {
                String body = "<p>Kindly click / tap on below link to verify your email address.</p>"
                        + "<a href=http://140.238.250.40:8080/resources/music/doemailverification?email=" + email + "><b>" + password + "</b></a>";
                emailUtility.sendEmail(email, "raagatech: email verification", body);
                response = "true";
            }
        } catch (Exception ex) {
            Logger.getLogger(RaagatechMusicApplication.class.getName()).log(Level.SEVERE, null, ex);
        }
        return response;
    }

    @RequestMapping(value = "/doemailverification", method = RequestMethod.GET)
    public String doVerifyEmail(@RequestParam("email") String email) {
        try {
            musicDataSource.updateUserForEmailVerification(email);
        } catch (Exception ex) {
            Logger.getLogger(RaagatechMusicApplication.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "e-mail verification successful. Thank you!";
    }

    @RequestMapping(value = "/doregisterinquiry", method = RequestMethod.POST)
    public String doRegisterInquiry(@RequestParam("inqName") String inquiryname,
            @RequestParam("inqEmail") String email, @RequestParam("inqMobile") String mobileNo, @RequestParam("inqGender") String gender,
            @RequestParam("inqPostalAddress") String address, @RequestParam("inqPinCode") int pinCode, @RequestParam("inqSubject") int subject,
            @RequestParam("inqYear") int year, @RequestParam("inqFollowupDetails") String followupDetails, @RequestParam("userId") int userId, @RequestParam("examSession") int examSession) {
        String response = "false";
        try {
            if (musicDataSource.insertInquiry(inquiryname, subject, email, Long.parseLong(mobileNo),
                    year, address, followupDetails, "091",
                    "", "", "", 0, "", gender,
                    "", "", "", userId, pinCode, examSession)) {
                String body = "<p>Thank you very much for showing interest in music learning and performance activities with us!"
                        + "To know more about our's effort and approaches, "
                        + "kindly browse through the website which is mentioned in this email signature.</p>";
                emailUtility.sendEmail(email, "raagatech: inquiry registration", body);
                response = "true";
            }
        } catch (Exception ex) {
            Logger.getLogger(RaagatechMusicApplication.class.getName()).log(Level.SEVERE, null, ex);
        }
        return response;
    }

    @RequestMapping(value = "/doselectlevel", method = RequestMethod.GET)
    public String doSelectLevel() throws Exception {
        String response;
        LinkedHashMap<Integer, String> levelMap = musicDataSource.selectLevel();
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
        LinkedHashMap<Integer, String> inspirationMap = musicDataSource.selectInspiration();
        if (!inspirationMap.isEmpty()) {
            response = commonUtilities.constructJSON("selectinspiration", inspirationMap);
        } else {
            response = commonUtilities.constructJSON("selectinspiration", false, "no data available");
        }
        return response;
    }

    @RequestMapping(value = "/dolistinquiry", method = RequestMethod.GET)
    public String doListInquiry(@RequestParam("userId") int userId) throws Exception {
        String response = null;
        ArrayList<InquiryBean> inquiryList = musicDataSource.listInquiry(userId);
        if (!inquiryList.isEmpty()) {
            JSONArray jsonArray = new JSONArray(inquiryList);
            response = jsonArray.toString();
        }
        return response;
    }

    @GetMapping(path = "/dogetinquiry/{inquiryId}")
    public String doGetInquiry(@PathVariable int inquiryId) throws Exception {
        String response = null;
        InquiryBean inquiry = musicDataSource.getInquiryById(inquiryId);
        if (inquiry != null) {
            JSONObject jsonObject = new JSONObject(inquiry);
            response = jsonObject.toString();
        }
        return response;
    }

    @RequestMapping(value = "/doupdateinquiry", method = RequestMethod.POST)
    public String doUpdateInquiry(@RequestParam("inqName") String inquiryname,
            @RequestParam("inqEmail") String email, @RequestParam("inqMobile") String mobileNo, @RequestParam("inqGender") String gender,
            @RequestParam("inqPostalAddress") String address, @RequestParam("inqPinCode") int pinCode, @RequestParam("inqSubject") int subject,
            @RequestParam("inqYear") int year, @RequestParam("inqFollowupDetails") String followupDetails,
            @RequestParam("userId") int userId, @RequestParam("inquiryId") int inquiry_id, @RequestParam("examSession") int examSession) {
        String response = "false";
        try {
            if (musicDataSource.updateInquiry(inquiry_id, inquiryname, subject, email, Long.parseLong(mobileNo),
                    year, address, followupDetails, "", "", "", "", 0, null,
                    gender, "", "", "", userId, pinCode, examSession)) {
                String body = "<p>Thank you very much for updating inquiry!"
                        + "To know more about our's effort and approaches, "
                        + "kindly browse through the website which is mentioned in this email signature.</p>";
                emailUtility.sendEmail(email, "raagatech: update inquiry", body);
                response = "true";
            }

        } catch (Exception ex) {
            Logger.getLogger(RaagatechMusicApplication.class.getName()).log(Level.SEVERE, null, ex);
        }
        return response;
    }

    @RequestMapping(value = "/doselectinquirystatus", method = RequestMethod.GET)
    public String doSelectInquiryStatus() throws Exception {
        String response;
        LinkedHashMap<Integer, String> inquiryStatusMap = musicDataSource.selectInquiryStatus();
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
                if (musicDataSource.updateFollowup(inquiry_id, inquirystatus_id, followupDetails)) {
                    result = 0;
                }

                InquiryBean inquiryBean = musicDataSource.getInquiryById(inquiry_id);
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

    @RequestMapping(value = "/dolistfavourites", method = RequestMethod.GET)
    public String doListInquiryFavourites(@RequestParam("username") String username, @RequestParam("password") String password) throws Exception {
        String response = null;
        ArrayList<UserDataBean> usersList = musicDataSource.getUsersList(username, password);
        if (!usersList.isEmpty()) {
            JSONArray jsonArray = new JSONArray(usersList);
            response = jsonArray.toString();
        }
        return response;
    }
}
