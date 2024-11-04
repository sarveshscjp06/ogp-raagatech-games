/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.raagatech.crm.customerapp;

import com.raagatech.bean.ProductCategoryBean;
import com.raagatech.bean.VendorDataBean;
import com.raagatech.common.datasource.CommonUtilitiesInterface;
import com.raagatech.samcrm.sales.VendorDataSource;
import com.sam.raagatech.ogp.samcrm.contacts.UserDataSource;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author sarve
 */
@RestController
@RequestMapping("/resources/login")
@ComponentScan("com.sam.raagatech.ogp.samcrm.contacts")
public class SamcrmCustomerLogon {

    @Autowired
    private UserDataSource userDataSource;
    @Autowired
    private CommonUtilitiesInterface commonUtilities;    
    @Autowired
    private VendorDataSource vendorDataSource;

    @GetMapping("/dologin")
    public String doLogin(@RequestParam("email") String userName, @RequestParam("mobile") String password) {
        String response = commonUtilities.constructJSON("login", false, "Invalid email or password");
        try {
            if (commonUtilities.isNotNull(userName) && Long.valueOf(password) > 0
                    && userDataSource.checkLogin(userName, Long.valueOf(password))) {
                response = commonUtilities.constructJSON("login", true);
            }
        } catch (Exception ex) {
            Logger.getLogger(SamcrmCustomerLogon.class.getName()).log(Level.SEVERE, null, ex);
        }
        return response;
    }

    @PostMapping("/samcrmAuthentication")
    public String authenticateSamCrmUser(@RequestBody com.fasterxml.jackson.databind.JsonNode userData) {
        return samcrmUserAuthentication(userData.toString());
    }

    @PostMapping("/samcrmUserData")
    public String checkSamcrmUserData(@RequestBody com.fasterxml.jackson.databind.JsonNode credentials) {

        try {
            return checkSamcrmCredentials(credentials.toString());
        } catch (Exception ex) {
            Logger.getLogger(SamcrmCustomerLogon.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @PostMapping("/logoutSamcrmUser")
    public String invalidateSamCrmUser(@RequestBody com.fasterxml.jackson.databind.JsonNode userData) {

        try {
            return logoutSamcrmUser(userData.toString());
        } catch (Exception ex) {
            Logger.getLogger(SamcrmCustomerLogon.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @GetMapping("/samcrmVendorsList")
    public String getSamcrmVendors(@RequestParam("mobile") String mobile, @RequestParam("postalCode") String zipCode, @RequestParam("vendorCategoryId") int category_id, @RequestParam("vendorSubtypeId") int subtype_id) {

        return getVendorsList(mobile, zipCode, category_id, subtype_id);
    }

    @GetMapping("/samcrmVendorCategoryList")
    public String getSamcrmVendorsCategory() {

        return getVendorCategoryList();
    }

    @GetMapping("/samcrmVendorSubTypeList")
    public String getSamcrmVendorsSubType() {

        return getVendorSubTypeList();
    }

    @GetMapping("/samcrmBlockCustomer")
    public String blockSamcrmCustomer(@RequestParam("vendor_id") int vendor_id, @RequestParam("customer_id") int customer_id, @RequestParam("mobile") int mobile) {

        return blockCustomer(vendor_id, customer_id, mobile);
    }

    @GetMapping("/samcrmBlockVendor")
    public String blockSamcrmVendor(@RequestParam("vendor_id") int vendor_id, @RequestParam("customer_id") int customer_id, @RequestParam("mobile") int mobile) {

        return blockVendor(vendor_id, customer_id, mobile);
    }

    private String samcrmUserAuthentication(String params) {

        JSONObject data = new JSONObject(params);
        String individual_id = null;
        String ipAddress = null;
        String name = null;
        String email = null;
        String mobile = null;
        String zipCode = null;
        String password = null;
        String vendorTitle = null;
        String vendorRegistrationNo = null;
        String vendorCategoryId = null;
        String vendorSubtypeId = null;
        String mobileVerificationCode = null;
        String vendorDescription = null;
        String pushMessage = null;
        String emailCampaignText = null;
        String bulkSmsText = null;
        String address = null;
        if (data.has("individual_id")) {
            individual_id = data.optString("individual_id");
        }
        if (data.has("ipAddress")) {
            ipAddress = data.getString("ipAddress");
        }
        if (data.has("name")) {
            name = data.getString("name");
        }
        if (data.has("email")) {
            email = data.getString("email");
        }
        if (data.has("mobile")) {
            mobile = data.getString("mobile");
        }
        if (data.has("zipCode")) {
            zipCode = data.getString("zipCode");
        }
        if (data.has("password")) {
            password = data.getString("password");
        }
        if (data.has("vendorTitle")) {
            vendorTitle = data.getString("vendorTitle");
        }
        if (data.has("vendorRegistrationNo")) {
            vendorRegistrationNo = data.getString("vendorRegistrationNo");
        }
        if (data.has("vendorCategoryId")) {
            vendorCategoryId = data.getString("vendorCategoryId");
        }
        if (data.has("vendorSubtypeId")) {
            vendorSubtypeId = data.getString("vendorSubtypeId");
        }
        if (data.has("mobileVerificationCode")) {
            mobileVerificationCode = data.getString("mobileVerificationCode");
        }
        if (data.has("vendorDescription")) {
            vendorDescription = data.getString("vendorDescription");
        }
        if (data.has("pushMessage")) {
            pushMessage = data.getString("pushMessage");
        }
        if (data.has("emailCampaignText")) {
            emailCampaignText = data.getString("emailCampaignText");
        }
        if (data.has("bulkSmsText")) {
            bulkSmsText = data.getString("bulkSmsText");
        }
        if (data.has("address")) {
            address = data.getString("address");
        }
        String response = commonUtilities.constructJSON("login", false, "SAM-CRM user authentiction failed");
        try {
            int insertStatus = 0;
//            if (commonUtilities.isNotNull(individual_id)) {
//                insertStatus = userDataSource.createSamcrmUser(name, email, mobile, zipCode, password, ipAddress, vendorCategoryId, vendorSubtypeId, vendorTitle, vendorRegistrationNo, individual_id);
//            } else 
            if (commonUtilities.isNotNull(individual_id) || (commonUtilities.isNotNull(mobileVerificationCode) && commonUtilities.isNotNull(name) && commonUtilities.isNotNull(email)
                    && commonUtilities.isNotNull(zipCode) && commonUtilities.isNotNull(password))) {
                insertStatus = userDataSource.addUpdateSamcrmUser(name, email, mobile, zipCode,
                        password, ipAddress, vendorCategoryId, vendorSubtypeId, vendorTitle, vendorRegistrationNo,
                        individual_id, vendorDescription, pushMessage, emailCampaignText, bulkSmsText, address);
            }
            if (insertStatus == 1) {
                LinkedHashMap<String, String> individualData = userDataSource.checkSamcrmLogin(mobile, ipAddress, null);
                if (individualData != null && !individualData.isEmpty()) {
                    ArrayList<VendorDataBean> vendorList = new ArrayList<>();
                    String userZipCode = individualData.get("zipCode");
                    String isVendor = individualData.get("isVendor");
                    if (Integer.parseInt(isVendor) == 0) {
                        vendorList = vendorDataSource.getAreaSpecificVendors(userZipCode, mobile, 0, 0);
                    }
                    response = commonUtilities.constructJSON(vendorList, true, individualData);
                    //EmailUtils.sendSamcrmUserAuthenticationEMail(email, name, "Congratulations! User Creation succeed!");
                } else {
                    response = commonUtilities.constructJSON("login", false, "User Registration Failed");
                }
            } else {
                int userStatus = userDataSource.getSamcrmUserStatus(mobile, password, ipAddress);
                switch (userStatus) {
                    case 8:
                    case 0:
//                        Random random = new Random();
//                        int max = 9999,
//                         min = 1000;
                        int otpNumber = 5571;//random.nextInt(max - min + 1) + min;
//                        String otpText = otpNumber + " is One Time Password for SAM-CRM mobile no verification. \nKindly don't share this with others.";
//                        otpText = otpText + " Thanks. For further inquiry please call or email: +919891029284 / raksha@raagatech.com";
//                        SMSUtils.sendSamcrmOtp(mobile, otpText);
                        response = commonUtilities.constructJSON("login", false, String.valueOf(otpNumber));
                        break;
                    case 1:
                    case 2:
                    case 4:
                    case 5:
                    case 6:
                        userDataSource.updateSamcrmUserStatus(mobile, ipAddress, userStatus);
                        LinkedHashMap<String, String> individualData = userDataSource.checkSamcrmLogin(mobile, ipAddress, null);
                        ArrayList<VendorDataBean> vendorList = new ArrayList<>();
                        String userZipCode = individualData.get("zipCode");
                        String isVendor = individualData.get("isVendor");
                        if (Integer.parseInt(isVendor) == 0) {
                            vendorList = vendorDataSource.getAreaSpecificVendors(userZipCode, mobile, 0, 0);
                        }
                        response = commonUtilities.constructJSON(vendorList, true, individualData);
                        //EmailUtils.sendSamcrmUserAuthenticationEMail(email, name, "Login attempted from different device and succeed!");
                        break;
                    case 7:
                        response = commonUtilities.constructJSON("login", false, "SAM-CRM login: mobile-no OR password is wrong.");
                        break;
                    default:
                        break;
                }
            }
        } catch (Exception e) {
        }
        return response;
    }

    private String checkSamcrmCredentials(String params) throws Exception {

        JSONObject data = new JSONObject(params);
        String mobileNo = null;
        String ipAddress = null;
        if (data.has("mobile") && !data.isNull("mobile")) {
            mobileNo = data.getString("mobile");
        }
        if (data.has("ipAddress")) {
            ipAddress = data.getString("ipAddress");
        }
        String response = commonUtilities.constructJSONString(null, null, false);
        try {
            if (mobileNo != null && !mobileNo.isEmpty()) {
                ArrayList<VendorDataBean> vendorList = new ArrayList<>();
                LinkedHashMap<String, String> individualData = userDataSource.checkSamcrmLogin(mobileNo, ipAddress, null);
                if (!individualData.isEmpty()) {
                    String zipCode = individualData.get("zipCode");
                    String isVendor = individualData.get("isVendor");
                    String mobile = individualData.get("mobile");
                    if (Integer.parseInt(isVendor) == 0) {
                        vendorList = vendorDataSource.getAreaSpecificVendors(zipCode, mobile, 0, 0);
                    }
                    response = commonUtilities.constructJSONString(vendorList, individualData, true);
                    //EmailUtils.sendSamcrmUserAuthenticationEMail(individualData.get("email"), individualData.get("name"), "Credentials Verification Succeed!");
                }
            }
        } catch (Exception e) {
        }
        return response;
    }

    private String logoutSamcrmUser(String params) throws Exception {

        JSONObject data = new JSONObject(params);
        String name = data.has("name") ? data.getString("name") : "";
        String email = data.has("email") ? data.getString("email") : "";
        String mobileNo = data.getString("mobile");
        String ipAddress = data.getString("ipAddress");
        String response = "";
        if (commonUtilities.isNotNull(mobileNo)) {
            try {
                int status = userDataSource.logoutSamcrmUser(mobileNo, ipAddress);

                if (status >= 1) {
                    response = commonUtilities.constructJSON("logoutsamcrmuser", true);
                    //EmailUtils.sendSamcrmUserAuthenticationEMail(email, name, "You are logged-out successfully.");
                } else {
                    response = commonUtilities.constructJSON("logoutsamcrmuser", false, "user not available");
                }
            } catch (Exception e) {
            }
        }
        return response;
    }

    private String getVendorsList(String mobile, String zipCode, int category_id, int subtype_id) {

        String response = commonUtilities.constructJSONString(null, null, false);
        try {
            ArrayList<VendorDataBean> vendorList = vendorDataSource.getAreaSpecificVendors(zipCode, mobile, category_id, subtype_id);
            response = commonUtilities.constructJSONString(vendorList, null, true);
        } catch (Exception e) {
        }
        return response;
    }

    private String getVendorCategoryList() {

        String response = commonUtilities.constructProductCategoryJSON(null, false, null);
        try {
            ArrayList<ProductCategoryBean> vendorCategoryList = vendorDataSource.selectVendorCategoryList();
            response = commonUtilities.constructProductCategoryJSON(vendorCategoryList, true, null);
        } catch (Exception e) {
        }
        return response;
    }

    private String getVendorSubTypeList() {

        String response = commonUtilities.constructProductCategoryJSON(null, false, null);
        try {
            ArrayList<ProductCategoryBean> vendorSubTypeList = vendorDataSource.selectVendorSubTypeList();
            response = commonUtilities.constructProductCategoryJSON(vendorSubTypeList, true, null);
        } catch (Exception e) {
        }
        return response;
    }

    private String blockCustomer(int vendor_id, int customer_id, int mobile) {

        String response = commonUtilities.constructJSON("Block Customer", false);
        try {
            userDataSource.deactivateCustomer(vendor_id, customer_id, mobile, 0);
            response = commonUtilities.constructJSON("Block Customer", true);
        } catch (Exception e) {
        }
        return response;
    }

    private String blockVendor(int vendor_id, int customer_id, int mobile) {

        String response = commonUtilities.constructJSON("Block Vendor", false);
        try {
            userDataSource.deactivateCustomer(vendor_id, customer_id, mobile, 1);
            response = commonUtilities.constructJSON("Block Vendor", true);
        } catch (Exception e) {
        }
        return response;
    }
}
