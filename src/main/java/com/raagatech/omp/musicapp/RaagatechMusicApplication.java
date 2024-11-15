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
        return "<h1>Spring Boot Hello World!</h1>"
                + "<br/> This service is about Raagatech Music Application"
                + "<br/>Inquiry -> Lead Generation -> Follow-up -> Interest Showing -> Confirm Registration -> Admission"
                + " -> Feedback -> PSS Exam -> Certified -> Meeting -> Contacts";
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
                Logger.getLogger(RaagatechMusicApplication.class.getName()).log(Level.SEVERE, null, e);
            }
        }
        return response;
    }

    @GetMapping(path = "/dogetuserdata/{userId}")
    public String doGetUserData(@PathVariable int userId) throws Exception {
        String response = null;
        UserDataBean userDataBean = musicDataSource.getUserData(userId);
        if (userDataBean != null) {
            JSONObject jsonObject = new JSONObject(userDataBean);
            response = jsonObject.toString();
        }
        return response;
    }

    @RequestMapping(value = "/doupdateuserdata", method = RequestMethod.POST)
    public String updateUserData(@RequestParam("regName") String username, @RequestParam("regPassword") String password,
            @RequestParam("regEmail") String email, @RequestParam("regMobile") String mobileNo, @RequestParam("regGender") String gender,
            @RequestParam("regPostalAddress") String postalAddress, @RequestParam("regPincode") String pincode,
            @RequestParam("userId") int userId, @RequestParam("regInspiratorId") int inspiratorId, @RequestParam("discount") int discount) {
        String response = "false";
        if (commonUtilities.isNotNull(username) && commonUtilities.isNotNull(password)) {
            try {
                musicDataSource.updateUserData(username, password, Long.parseLong(mobileNo),
                        gender, postalAddress, pincode, userId, inspiratorId, discount);
                response = "true";
            } catch (Exception e) {
                Logger.getLogger(RaagatechMusicApplication.class.getName()).log(Level.SEVERE, null, e);
            }
        }
        return response;
    }

    @RequestMapping(value = "/doregister", method = RequestMethod.POST)
    public String doRegister(@RequestParam("regName") String username, @RequestParam("regPassword") String password,
            @RequestParam("regEmail") String email, @RequestParam("regMobile") String mobileNo, @RequestParam("regGender") String gender,
            @RequestParam("regPostalAddress") String postalAddress, @RequestParam("regPincode") String pincode,
            @RequestParam("regInspiratorId") int inspiratorId, @RequestParam("discount") int discount,
            @RequestParam("userId") int user_id) {

        String response = "false";
        try {
            int userId = musicDataSource.insertUser(username, password, email, Long.parseLong(mobileNo), gender, postalAddress, pincode, inspiratorId, discount);
            if (userId > 0) {
                String body = "<p>Kindly click / tap on below link to verify your email address.</p>"
                        + "<a href=http://140.238.250.40:8080/resources/music/doemailverification?userId=" + userId + "&email=" + email + "><b>" + password + "</b></a>";
                emailUtility.sendEmail(email, "raagatech: email verification", body);
                response = "true";
            }
        } catch (Exception ex) {
            Logger.getLogger(RaagatechMusicApplication.class.getName()).log(Level.SEVERE, null, ex);
        }
        return response;
    }

    @RequestMapping(value = "/doemailverification", method = RequestMethod.GET)
    public String doVerifyEmail(@RequestParam("userId") int userId, @RequestParam("email") String email) {
        String verificationStatus = "e-mail verification failure. May be server is down. "
                .concat("Kindly contact to admin on mobile: 9891029284.");
        try {
            if (musicDataSource.updateUserForEmailVerification(userId, email)) {
                verificationStatus = "e-mail verification successful. Thank you!";
            }
        } catch (Exception ex) {
            Logger.getLogger(RaagatechMusicApplication.class.getName()).log(Level.SEVERE, null, ex);
        }
        return verificationStatus;
    }

    @RequestMapping(value = "/domobileverification", method = RequestMethod.GET)
    public String doVerifyMobile(@RequestParam("userId") int userId, @RequestParam("email") String email, @RequestParam("mobile") long mobile) {
        String verificationStatus = "mobile no verification failure. May be server is down. "
                .concat("Kindly contact to admin on mobile: 9891029284.");
        try {
            if (musicDataSource.updateUserForMobileVerification(userId, email, mobile)) {
                verificationStatus = "mobile verification successful. Thank you!";
            }
        } catch (Exception ex) {
            Logger.getLogger(RaagatechMusicApplication.class.getName()).log(Level.SEVERE, null, ex);
        }
        return verificationStatus;
    }

    @RequestMapping(value = "/doregisterinquiry", method = RequestMethod.POST)
    public String doRegisterInquiry(@RequestParam("inqName") String inquiryname,
            @RequestParam("inqEmail") String email, @RequestParam("inqMobile") String mobileNo, @RequestParam("inqGender") String gender,
            @RequestParam("inqPostalAddress") String address, @RequestParam("inqPinCode") int pinCode, @RequestParam("inqSubject") int subject,
            @RequestParam("inqYear") int year, @RequestParam("inqFollowupDetails") String followupDetails,
            @RequestParam("userId") int userId, @RequestParam("examSession") String examSession,
            @RequestParam("inqEducation") String primaryskill, @RequestParam("inqDob") String dob,
            @RequestParam("inqFatherName") String fatherName, @RequestParam("inqMotherName") String motherName,
            @RequestParam("inqExamFees") int inqExamFees, @RequestParam("inspiratorId") int inspiratorId, @RequestParam("inquiryStatus") int inquiryStatusId) {
        String response = "false";
        try {
            if (musicDataSource.insertInquiry(inquiryname, subject, email, Long.parseLong(mobileNo),
                    year, address, followupDetails, "091",
                    dob, 0, "", gender,
                    inspiratorId, "", primaryskill, userId, pinCode, examSession, fatherName, motherName, inqExamFees, inquiryStatusId)) {
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
    public String doListInquiry(@RequestParam("userId") int userId, @RequestParam("inspiratorId") int inspiratorId,
            @RequestParam("examSession") String examSession, @RequestParam("inquiryStatusId") int inquiryStatusId) throws Exception {
        String response = null;
        if (inquiryStatusId == 0) {
            inquiryStatusId = 1;
        }
        ArrayList<InquiryBean> inquiryList = musicDataSource.listInquiry(userId, inspiratorId, examSession, inquiryStatusId);
        if (!inquiryList.isEmpty()) {
            JSONArray jsonArray = new JSONArray(inquiryList);
            response = jsonArray.toString();
        }
        return response;
    }

    @GetMapping(path = "/dogetinquiry/{inquiryId}/{followUpId}")
    public String doGetInquiry(@PathVariable int inquiryId, @PathVariable String followUpId) throws Exception {
        String response = null;
        InquiryBean inquiry = musicDataSource.getInquiryById(inquiryId, followUpId);
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
            @RequestParam("userId") int userId, @RequestParam("inquiryId") int inquiry_id, @RequestParam("examSession") String examSession,
            @RequestParam("inqEducation") String primaryskill, @RequestParam("inqDob") String dob,
            @RequestParam("inqFatherName") String fatherName, @RequestParam("inqMotherName") String motherName,
            @RequestParam("inqExamFees") int inqExamFees, @RequestParam("inquiryStatus") int inquiryStatusId, @RequestParam("inspiratorId") int inspiratorId) {
        String response = "false";
        try {
            if (musicDataSource.updateInquiry(inquiry_id, inquiryname, subject, email, Long.parseLong(mobileNo),
                    year, address, followupDetails, "", dob, 0, null,
                    gender, inspiratorId, "", primaryskill, userId, pinCode, examSession, fatherName, motherName,
                    inqExamFees, inquiryStatusId)) {
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
            @RequestParam("followupDetails") String followupDetails, @RequestParam("followUpId") String followUpId) {
        String response;
        int result = updateFollowup(Integer.parseInt(inquiry_id), Integer.parseInt(inquirystatus_id), followupDetails, followUpId);
        if (result == 0) {
            response = commonUtilities.constructJSON("register", true);
        } else {
            response = commonUtilities.constructJSON("register", false, "sql insertion error occurred");
        }
        return response;
    }

    private int updateFollowup(int inquiry_id, int inquirystatus_id, String followupDetails, String followUpId) {

        int result = 3;
        if (inquiry_id > 0 && commonUtilities.isNotNull(followupDetails) && inquirystatus_id > 0) {
            try {
                if (musicDataSource.updateFollowup(inquiry_id, inquirystatus_id, followupDetails)) {
                    result = 0;
                }

                InquiryBean inquiryBean = musicDataSource.getInquiryById(inquiry_id, followUpId);
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

    @RequestMapping(value = "/doinquiryemailverification", method = RequestMethod.GET)
    public String doVerifyInquiryEmail(@RequestParam("inquiryId") int inquiryId, @RequestParam("email") String email) {
        String verificationStatus = "e-mail verification successful. Thank you!";
        try {
            if (!musicDataSource.updateInquiryForEmailVerification(inquiryId, email)) {
                verificationStatus = "e-mail verification failure. May be server is down. "
                        .concat("Kindly contact to admin on mobile: 9891029284.");
            }
        } catch (Exception ex) {
            Logger.getLogger(RaagatechMusicApplication.class.getName()).log(Level.SEVERE, null, ex);
        }
        return verificationStatus;
    }

    @RequestMapping(value = "/doinquirymobileverification", method = RequestMethod.GET)
    public String doVerifyInquiryMobile(@RequestParam("inquiryId") int inquiryId, @RequestParam("mobile") long mobile) {
        String verificationStatus = "mobile verification successful. Thank you!";
        try {
            if (!musicDataSource.updateInquiryForMobileVerification(inquiryId, mobile)) {
                verificationStatus = "mobile no verification failure. May be server is down. "
                        .concat("Kindly contact to admin on mobile: 9891029284.");
            }
        } catch (Exception ex) {
            Logger.getLogger(RaagatechMusicApplication.class.getName()).log(Level.SEVERE, null, ex);
        }
        return verificationStatus;
    }

    @RequestMapping(value = "/doinquiryfeespaidstatus", method = RequestMethod.GET)
    public String doUpdateFeesPaidStatus(@RequestParam("inquiryId") int inquiryId, @RequestParam("userId") int userId, @RequestParam("amount") int amount) {
        String verificationStatus = "Exam Fees Payment successful. Thank you!";
        try {
            if (userId == 2 && !musicDataSource.updateInquiryForFeesPaidStatus(inquiryId, amount)) {
                verificationStatus = "Exam Fees Payment failure. May be server is down. "
                        .concat("Kindly contact to admin on mobile: 9891029284.");
            }
        } catch (Exception ex) {
            Logger.getLogger(RaagatechMusicApplication.class.getName()).log(Level.SEVERE, null, ex);
        }
        return verificationStatus;
    }

    @RequestMapping(value = "/dogeneratepssexamreport", method = RequestMethod.GET)
    public String doGeneratePssExamReport(@RequestParam("userId") int userId, @RequestParam("inspiratorId") int inspiratorId,
            @RequestParam("examSession") String examSession, @RequestParam("inquiryStatusId") int inquiryStatusId,
            @RequestParam("reportType") int reportType) throws Exception {
        String response = null;

        ArrayList<PssExamReportBean> pssExamReport = musicDataSource.generatePssExamReport(userId, inspiratorId, examSession, inquiryStatusId, reportType);
        if (!pssExamReport.isEmpty()) {
            JSONArray jsonArray = new JSONArray(pssExamReport);
            response = jsonArray.toString();
        }
        return response;
    }

    @RequestMapping(value = "/dolisteducators", method = RequestMethod.GET)
    public String doListEducators(@RequestParam("userId") int userId, @RequestParam("examSession") String examSession) throws Exception {
        String response = null;

        ArrayList<UserDataBean> usersList = musicDataSource.listEducators(userId, examSession);
        if (!usersList.isEmpty()) {
            JSONArray jsonArray = new JSONArray(usersList);
            response = jsonArray.toString();
        }
        return response;
    }

    @GetMapping(path = "/dogeteducatordata/{userId}/{inspiratorId}")
    public String doGetEducatorData(@PathVariable int userId, @PathVariable int inspiratorId) throws Exception {
        String response = null;
        UserDataBean userDataBean = musicDataSource.getEducatorData(userId, inspiratorId);
        if (userDataBean != null) {
            JSONObject jsonObject = new JSONObject(userDataBean);
            response = jsonObject.toString();
        }
        return response;
    }

    @RequestMapping(value = "/doregistereducator", method = RequestMethod.POST)
    public String doRegisterEducator(@RequestParam("eduName") String username, @RequestParam("eduSpecialisation") String specialisation,
            @RequestParam("eduEmail") String email, @RequestParam("eduMobile") String mobileNo, @RequestParam("eduGender") String gender,
            @RequestParam("eduPostalAddress") String postalAddress, @RequestParam("eduPincode") String pincode,
            @RequestParam("eduDiscount") int discount, @RequestParam("userId") int userId) {

        String response = "false";
        try {
            if (musicDataSource.createEducator(username, specialisation, email, Long.parseLong(mobileNo), gender, postalAddress, pincode, userId, discount)) {
                response = "true";
            }

        } catch (Exception ex) {
            Logger.getLogger(RaagatechMusicApplication.class.getName()).log(Level.SEVERE, null, ex);
        }
        return response;
    }
    
    @RequestMapping(value = "/doupdateeducatordata", method = RequestMethod.POST)
    public String doUpdateEducator(@RequestParam("eduName") String username, @RequestParam("eduSpecialisation") String specialisation,
            @RequestParam("eduEmail") String email, @RequestParam("eduMobile") String mobileNo, @RequestParam("eduGender") String gender,
            @RequestParam("eduPostalAddress") String postalAddress, @RequestParam("eduPincode") String pincode,
            @RequestParam("userId") int userId, @RequestParam("eduInspiratorId") int eduInspiratorId, @RequestParam("eduDiscount") int discount) {

        String response = "false";
        try {
            if (musicDataSource.updateEducator(username, specialisation, email, Long.parseLong(mobileNo), gender, postalAddress, pincode, userId, eduInspiratorId, discount)) {
                response = "true";
            }

        } catch (Exception ex) {
            Logger.getLogger(RaagatechMusicApplication.class.getName()).log(Level.SEVERE, null, ex);
        }
        return response;
    }
}
