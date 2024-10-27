/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.raagatech.sam.merchantapp;

import com.raagatech.bean.ProductCategoryBean;
import com.raagatech.bean.ProductsAndServicesBean;
import com.raagatech.bean.SliderImageBean;
import com.raagatech.bean.VendorDataBean;
import com.raagatech.common.datasource.CommonUtilitiesInterface;
import com.raagatech.common.datasource.EmailUtilityInterface;
import com.raagatech.samcrm.sales.VendorDataSource;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author sarve
 */
@RestController
@RequestMapping("/resources/vendor")
public class SamcrmSalesApplication {

    @Autowired
    private VendorDataSource vendorDataSource;
    @Autowired
    private CommonUtilitiesInterface commonUtilities;
    @Autowired
    private EmailUtilityInterface emailUtility;

    @RequestMapping
    public String home() {

        return "<h1>Spring Boot Hello World!</h1><br/> This service is about SAMCRM Sale Application"
                + "<br/>Sales focuses on closing deals and generating revenue."
                + "<br/>I.E. Sales involve direct interaction with potential customers, actively persuading them to purchase a product or service.";
    }

    @RequestMapping(value = "/samcrmSelectVendor", method = RequestMethod.GET)
    public String doSelectVendor() throws Exception {
        String response;
        LinkedHashMap<Integer, String> categoryMap = vendorDataSource.selectVendorCategory();
        if (!categoryMap.isEmpty()) {
            response = commonUtilities.constructJSON("selectvendor", categoryMap);
        } else {
            response = commonUtilities.constructJSON("selectvendor", false, "no data available");
        }
        return response;
    }

    @RequestMapping(value = "/samcrmSelectSubtype", method = RequestMethod.GET)
    public String doSelectSubtype() throws Exception {
        String response;
        LinkedHashMap<Integer, String> subtypeMap = vendorDataSource.selectVendorSubtype();
        if (!subtypeMap.isEmpty()) {
            response = commonUtilities.constructJSON("selectsubtype", subtypeMap);
        } else {
            response = commonUtilities.constructJSON("selectsubtype", false, "no data available");
        }
        return response;
    }

    @RequestMapping(value = "/samcrmVendorData", method = RequestMethod.GET)
    public String getVendorData(@RequestParam("vendorcategory") String vendorCategory, @RequestParam("vendorsubtype") String vendorSubType, @RequestParam("vendorid") String vendorId) throws Exception {
        String response;
        LinkedHashMap<Integer, String> categoryMap = vendorDataSource.selectVendorCategory();
        if (!categoryMap.isEmpty()) {
            response = commonUtilities.constructJSON("selectvendor", categoryMap);
        } else {
            response = commonUtilities.constructJSON("selectvendor", false, "no data available");
        }
        return response;
    }

    @RequestMapping(value = "/samcrmSliderImage", method = RequestMethod.GET)
    public String getSamcrmSliderImage() throws Exception {
        String response;
        ArrayList<SliderImageBean> sliderImageList = vendorDataSource.listSliderImage();
        if (!sliderImageList.isEmpty()) {
            response = commonUtilities.constructJSON_ForSliderImage("samcrmsliderimage", sliderImageList);
        } else {
            response = commonUtilities.constructJSON("samcrmsliderimage", false, "no data available");
        }
        return response;
    }

    @RequestMapping(value = "/samcrmProductCategory", method = RequestMethod.POST)
    public String getProductCategory(@RequestBody com.fasterxml.jackson.databind.JsonNode mRequestBody) throws Exception {
        String response;
        JSONObject data = new JSONObject(mRequestBody.toString());
        String vendorId = data.getString("vendorId");
        ArrayList<ProductCategoryBean> productCategoryList = vendorDataSource.selectProductCategoryDetails(vendorId);
        if (!productCategoryList.isEmpty()) {
            JSONObject object = new JSONObject();
            object.put("samcrmProductCategory", productCategoryList);

            for (ProductCategoryBean productCategoryBean : productCategoryList) {
                ArrayList<ProductsAndServicesBean> productsList = vendorDataSource.selectProductsAndServicesDetails(productCategoryBean.getProductCategoryId());
                if (!productsList.isEmpty()) {
                    object.put(productCategoryBean.getProductCategoryName(), productsList);
                } else {
                    object.put(productCategoryBean.getProductCategoryName(), new ArrayList<>());
                }
            }
            response = commonUtilities.constructJSON("samcrmProductCategory", true, object.toString());
        } else {
            response = commonUtilities.constructJSON("samcrmProductCategory", false, "no data available");
        }
        return response;
    }

    @RequestMapping(value = "/samcrmProductCategorysOnly", method = RequestMethod.POST)
    public String getProductCategorysOnly(@RequestBody com.fasterxml.jackson.databind.JsonNode mRequestBody) throws Exception {
        String response;
        JSONObject data = new JSONObject(mRequestBody.toString());
        String vendorId = data.getString("vendorId");

        ArrayList<ProductCategoryBean> productCategoryList;

//        LinkedHashMap<String, String> individualData = SamcrmDataFactory.getUserDataInstance().checkSamcrmLogin(null, null, vendorId);
//        if (individualData.isEmpty()) {
//            response = commonUtilities.constructProductCategoryJSON(productCategoryList,
//                    false, "Kindly login to the SAM-CRM App.");
//            return response;
//        }

        productCategoryList = vendorDataSource.selectProductCategoryDetails(vendorId);
        if (!productCategoryList.isEmpty()) {
            response = commonUtilities.constructProductCategoryJSON(productCategoryList,
                    true, "This service provides vendor specific product categories.");
        } else {
            response = commonUtilities.constructProductCategoryJSON(productCategoryList,
                    false, "no data available");
        }
        return response;
    }

    @RequestMapping(value = "/samcrmProductItems", method = RequestMethod.POST)
    public String getProductItems(@RequestBody com.fasterxml.jackson.databind.JsonNode mRequestBody) throws Exception {
        String response;
        JSONObject data = new JSONObject(mRequestBody.toString());
//        String vendorId = data.getString("vendorId");
        String productCategoryId = data.getString("productCategoryId");

        ArrayList<ProductsAndServicesBean> itemsList = vendorDataSource.selectProductsAndServicesDetails(productCategoryId);
        if (!itemsList.isEmpty()) {
            response = commonUtilities.constructProductItemsJSON(itemsList,
                    true, "This service provides product category specific items.");
        } else {
            response = commonUtilities.constructProductItemsJSON(itemsList,
                    false, "no data available");
        }
        return response;
    }

    @RequestMapping(value = "/samcrmProductsDetails", method = RequestMethod.GET)
    public String getProductsAndServicesDetails(@RequestParam("vendorcategory") String vendorCategory, @RequestParam("vendorsubtype") String vendorSubType, @RequestParam("vendorid") String vendorId) throws Exception {
        String response;
        ArrayList<ProductsAndServicesBean> productsList = vendorDataSource.selectProductsAndServicesDetails(vendorCategory);
        if (!productsList.isEmpty()) {
            JSONArray object = new JSONArray();
            object.put(productsList);
            response = object.toString();
        } else {
            response = commonUtilities.constructJSON("samcrmProductsDetails", false, "no data available");
        }
        return response;
    }

    @RequestMapping(value = "/samcrmAddContact", method = RequestMethod.POST)
    public String addSamcrmContact(@RequestBody com.fasterxml.jackson.databind.JsonNode params) {

        JSONObject data = new JSONObject(params.toString());
        String contactId = data.getString("customerId");
        String name = data.getString("name");
        String email = data.getString("email");
        String mobile = data.getString("mobile");
        String zipCode = data.getString("zipCode");
        String dob = data.getString("dob");
        String address = data.getString("address");
        String comment = data.getString("comment");
        String message = data.getString("message");
        String vendor_group_id = data.getString("vendor_group_id");

        String response = commonUtilities.constructJSON("register", false, "SAM-CRM adding contact failed");
        if (commonUtilities.isNotNull(mobile)) {
            try {
                int insertStatus = vendorDataSource.addContact(name, email, mobile, dob, address, zipCode,
                        comment, vendor_group_id, contactId, message);
                if (insertStatus >= 1) {
                    response = commonUtilities.constructJSON("register", true, "SAM-CRM contact added / updated successfully!");
                }
            } catch (Exception e) {
            }
        }
        return response;
    }

    @RequestMapping(value = "/samcrmListContacts", method = RequestMethod.POST)
    public String getContactsList(@RequestBody com.fasterxml.jackson.databind.JsonNode params) throws Exception {
        String vendor_group_id = new JSONObject(params.toString()).getString("vendor_group_id");
        String response;
        ArrayList<VendorDataBean> contactList = vendorDataSource.selectContacts(vendor_group_id);
        if (!contactList.isEmpty()) {
            response = commonUtilities.constructContactJSON("listcontact", true, contactList);
        } else {
            response = commonUtilities.constructContactJSON("listcontact", false, contactList);
        }
        return response;
    }

    @RequestMapping(value = "/samcrmUpdateProductCategory", method = RequestMethod.POST)
    public String updateProductCategory(@RequestBody com.fasterxml.jackson.databind.JsonNode params) {

        JSONObject data = new JSONObject(params.toString());
        String categoryId = data.getString("categoryId");
        String categoryName = data.getString("categoryName");
        String categoryDescription = data.getString("categoryDescription");
        String categorySequence = data.getString("categorySequence");
        String vendor_group_id = data.getString("vendor_group_id");

        String response = commonUtilities.constructJSON("update", false, "SAM-CRM Product Category");
        if (commonUtilities.isNotNull(vendor_group_id)) {
            try {
                String vendorName;
                if (categoryId != null && Integer.parseInt(categoryId) > 0) {
                    vendorName = vendorDataSource.editCategory(Integer.parseInt(categoryId), categoryName, categoryDescription, vendor_group_id, Integer.parseInt(categorySequence));
                } else {
                    vendorName = vendorDataSource.addCategory(categoryName, categoryDescription, vendor_group_id, Integer.parseInt(categorySequence));
                }
                if (vendorName != null) {
                    response = commonUtilities.constructJSON("update",
                            true, "Hi " + vendorName + ", you have added / updated a product category!");
                }
            } catch (Exception e) {
            }
        }
        return response;
    }

    @RequestMapping(value = "/samcrmUpdateProductAndService", method = RequestMethod.POST)
    public String updateProductAndService(@RequestBody com.fasterxml.jackson.databind.JsonNode params) {

        JSONObject data = new JSONObject(params.toString());
        String productId = data.getString("categoryId");
        String productName = data.getString("categoryName");
        String productDescription = data.getString("categoryDescription");
        String productSequence = data.getString("categorySequence");
        String vendor_group_id = data.getString("vendor_group_id");
        int product_category_id = Integer.parseInt(data.getString("product_category_id"));
        int price = Integer.parseInt(data.getString("price"));
        int discount = Integer.parseInt(data.getString("discount"));

        String response = commonUtilities.constructJSON("update", false, "SAM-CRM Product And Service Updation");
        if (commonUtilities.isNotNull(vendor_group_id)) {
            try {
                String vendorName;
                if (productId != null && Integer.parseInt(productId) > 0) {
                    vendorName = vendorDataSource.editProductAndService(Integer.parseInt(productId), productName, productDescription, vendor_group_id,
                            Integer.parseInt(productSequence), price, discount, product_category_id);
                } else {
                    vendorName = vendorDataSource.addProductAndService(productName, productDescription, vendor_group_id, price, discount, product_category_id, Integer.parseInt(productSequence));
                }
                if (vendorName != null) {
                    response = commonUtilities.constructJSON("update", true, vendorName);
                    emailUtility.pushSalesAndServicesViaEmail("SAM-CRM", "sarvesh.new@gmail.com", "sarveshscjp06@yahoo.co.in", "New Product Offer!", "in daily email /sms, the vendor can share about the new products and / or services", "SCJP06");//"I'm xyz, My shop is here: wer  rwerwe. Below is the offer description: "
                }
            } catch (Exception e) {
            }
        }
        return response;
    }

    @RequestMapping(value = "/samcrmInventoryEntry", method = RequestMethod.GET)
    public String updateProductServiceInventory(@RequestParam("productId") int productId, @RequestParam("quantity") int quantity, @RequestParam("unit") String unit, @RequestParam("vendorId") String vendor_group_id) {

        String response = commonUtilities.constructJSON("update", false, "SAM-CRM Product Service Inventory Entry");
        if (commonUtilities.isNotNull(vendor_group_id)) {
            try {
                String inventoryStatus;
                if (productId > 0) {
                    inventoryStatus = vendorDataSource.updateProductServiceInventory(productId, quantity, unit);
                    response = commonUtilities.constructJSON("update", true, inventoryStatus);
                }
            } catch (Exception e) {
            }
        }
        return response;
    }

    @RequestMapping(value = "/samcrmCheckInventory", method = RequestMethod.GET)
    public String CheckInventory(@RequestParam("productId") int productId, @RequestParam("vendorId") String vendor_group_id) {

        String response = commonUtilities.constructJSON("check-inventory", false, "SAM-CRM Check Inventory");
        if (commonUtilities.isNotNull(vendor_group_id)) {
            try {
                if (productId > 0) {
                    int inventoryStatus = vendorDataSource.checkInventory(productId);
                    response = commonUtilities.constructJSON("check-inventory", true, String.valueOf(inventoryStatus));
                }
            } catch (Exception e) {
            }
        }
        return response;
    }

    @RequestMapping(value = "/samcrmGenerateCoupon", method = RequestMethod.GET)
    public String generateCoupon(@RequestParam("vendorId") int vendor_id, @RequestParam("productId") int productId, @RequestParam("customerId") int customerId) {
        String response = commonUtilities.constructJSON("generate-coupon", false);
        boolean isCouponGenerated = Boolean.FALSE;
        if (vendor_id > 0) {
            try {
                if (productId > 0) {
                    //isCouponGenerated = vendorDataSource.productwiseCouponGeneration(productId);
                    if (isCouponGenerated) {
                        response = commonUtilities.constructJSON("productwise_coupon_generation", true);
                    }
                } else if (customerId > 0) {
                    //isCouponGenerated = vendorDataSource.customerwiseCouponGeneration(customerId);
                    if (isCouponGenerated) {
                        response = commonUtilities.constructJSON("customerwise_coupon_generation", true);
                    }
                } else //isCouponGenerated = vendorDataSource.commonCouponGeneration();
                if (isCouponGenerated) {
                    response = commonUtilities.constructJSON("common_coupon_generation", true);
                }
            } catch (Exception e) {
            }
        }

        return response;
    }

    @RequestMapping(value = "/samcrmPushSalesAndService", method = RequestMethod.POST)
    public String pushSalesAndService(@RequestBody com.fasterxml.jackson.databind.JsonNode params) throws Exception {

        JSONObject data = new JSONObject(params.toString());
        int vendor_id = data.has("vendorId") ? Integer.parseInt(data.getString("vendorId")) : 0;
        String vendorTitle = data.has("vendorTitle") ? data.getString("vendorTitle") : "";
        String name = data.has("name") ? data.getString("name") : "";
        String email = data.has("email") ? data.getString("email") : "";
        String mobile = data.has("mobile") ? data.getString("mobile") : "";
        String message = data.has("message") ? data.getString("message") : "";
//        String ipAddress = data.getString("ipAddress");
        String address = data.has("address") ? data.getString("address") : "";
        String response = commonUtilities.constructJSON("Push Sales & Service", false);
        if (vendor_id > 0) {
            try {
                ArrayList<VendorDataBean> contactList = vendorDataSource.selectContacts(String.valueOf(vendor_id));
                for (VendorDataBean bean : contactList) {
                    message = "Hi " + bean.getVendorTitle() + " " + bean.getName() + ",\n Greetings!\n" + message;
                    message = message + "\n Thank you. For further inquiry please call : " + mobile + " OR e-mail: " + email + " us!";
                    message = message + "\n Best Regards\n" + vendorTitle + "\n\n" + name + "\n" + address;
//                    SMSUtils.sendSamcrmOtp(String.valueOf(bean.getMobile()), message);
                    emailUtility.pushSalesAndServicesViaEmail(vendorTitle, email, bean.getEmail(), "Discount Offer!", message, bean.getName());//"I'm xyz, My shop is here: wer  rwerwe. Below is the offer description: "
                }
                response = commonUtilities.constructJSON("Push Sales & Service", true);
            } catch (Exception e) {
            }
        }

        return response;
    }

    @RequestMapping(value = "/samcrmEmailCampaign", method = RequestMethod.POST)
    public String emailCampaign(@RequestBody com.fasterxml.jackson.databind.JsonNode params) throws Exception {

        JSONObject data = new JSONObject(params.toString());
        int vendor_id = data.has("vendorId") ? Integer.parseInt(data.getString("vendorId")) : 0;
        String vendorTitle = data.has("vendorTitle") ? data.getString("vendorTitle") : "";
        String name = data.has("name") ? data.getString("name") : "";
        String email = data.has("email") ? data.getString("email") : "";
        String mobile = data.has("mobile") ? data.getString("mobile") : "";
        String message = data.has("message") ? data.getString("message") : "";
//        String ipAddress = data.getString("ipAddress");
        String address = data.has("address") ? data.getString("address") : "";
        String response = commonUtilities.constructJSON("E-mail Campaign", false);
        if (vendor_id > 0) {
            try {
                ArrayList<VendorDataBean> contactList = vendorDataSource.selectContacts(String.valueOf(vendor_id));
                for (VendorDataBean bean : contactList) {
                    message = "Hi " + bean.getVendorTitle() + " " + bean.getName() + ",\n Greetings!\n" + message;
                    message = message + "\n Thank you. For further inquiry please call : " + mobile + " OR e-mail: " + email + " us!";
                    message = message + "\n Best Regards\n" + name + "\n" + address;
                    emailUtility.pushSalesAndServicesViaEmail(vendorTitle, email, bean.getEmail(), "!Offer! for today only.", message, bean.getName());//"I'm xyz, My shop is here: wer  rwerwe. Below is the offer description: "
                }
                response = commonUtilities.constructJSON("E-mail Campaign", true);
            } catch (Exception e) {
            }
        }

        return response;
    }

    @RequestMapping(value = "/samcrmBulkSms", method = RequestMethod.POST)
    public String bulkSms(@RequestBody com.fasterxml.jackson.databind.JsonNode params) throws Exception {

        JSONObject data = new JSONObject(params.toString());
        int vendor_id = data.has("vendorId") ? Integer.parseInt(data.getString("vendorId")) : 0;
        String vendorTitle = data.has("vendorTitle") ? data.getString("vendorTitle") : "";
        String name = data.has("name") ? data.getString("name") : "";
        String email = data.has("email") ? data.getString("email") : "";
        String mobile = data.has("mobile") ? data.getString("mobile") : "";
        String message = data.has("message") ? data.getString("message") : "";
//        String ipAddress = data.getString("ipAddress");
        String address = data.has("address") ? data.getString("address") : "";
        String response = commonUtilities.constructJSON("SMS Campaign", false);
        if (vendor_id > 0) {
            try {
                ArrayList<VendorDataBean> contactList = vendorDataSource.selectContacts(String.valueOf(vendor_id));
                for (VendorDataBean bean : contactList) {
                    message = "Hi " + bean.getVendorTitle() + " " + bean.getName() + ",\n Greetings!\n" + message;
                    message = message + "\n Thank you. For further inquiry please call : " + mobile + " OR e-mail: " + email + " us!";
                    message = message + "\n Best Regards\n" + vendorTitle + "\n\n" + name + "\n" + address;
//                    SMSUtils.sendSamcrmOtp(String.valueOf(bean.getMobile()), message);
                }
                response = commonUtilities.constructJSON("SMS Campaign", true);
            } catch (Exception e) {
            }
        }

        return response;
    }
}
