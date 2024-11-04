/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.raagatech.sam.marketingapp;

import com.raagatech.bean.CustomerAddressBean;
import com.raagatech.bean.OrderDataBean;
import com.raagatech.bean.OrderTrackerBean;
import com.raagatech.bean.OrderedItemBean;
import com.raagatech.bean.PaymentBean;
import com.raagatech.bean.UserDataBean;
import com.raagatech.common.datasource.CommonUtilitiesInterface;
import com.raagatech.common.datasource.EmailUtilityInterface;
import com.raagatech.samcrm.marketing.OrderBookingDataSource;
import com.raagatech.samcrm.sales.VendorDataSource;
import com.sam.raagatech.ogp.samcrm.contacts.UserDataSource;
import java.util.ArrayList;
import java.util.Random;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
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
@RequestMapping("/resources/order")
@ComponentScan("com.raagatech.samcrm.marketing")
public class SamcrmMarketingApplication {

    @Autowired
    private VendorDataSource vendorDataSource;
    @Autowired
    private OrderBookingDataSource orderBookingDataSource;
    @Autowired
    private CommonUtilitiesInterface commonUtilities;
    @Autowired
    private EmailUtilityInterface emailUtility;
    @Autowired
    private UserDataSource userDataSource;

    @RequestMapping
    public String home() {
        return "<h1>Spring Boot Hello World!</h1><br/> This service is about SAMCRM Marketing Application "
                + "<br/>Marketing encompasses a broader set of activities to create awareness, generate leads, and build customer relationships "
                + "<br/>1. Email marketing 2. Content marketing 3. Search engine optimization 4. Social media marketing "
                + "5. Blogging 6. Influencer marketing 7. Word of mouth 8. Advertising 9. Event marketing 10. Marketing communications "
                + "11. Conduct market research 12. Define your budget 13. Direct marketing 14. Identify goals 15. Marketing budget "
                + "16. Marketing objectives 17. Online 18. Public Relations 18. Online marketing 19. Conversational marketing "
                + "20. Cause marketing 21. Affiliate marketing 22. Inbound marketing";
    }

    @RequestMapping(value = "/samcrmBookOrder", method = RequestMethod.POST)
    public String bookOrder(@RequestBody com.fasterxml.jackson.databind.JsonNode orderData) {

        JSONObject data = new JSONObject(orderData.toString());
        int vendorId = data.getInt("vendorId");//1
        int customerId = data.getInt("customerId");//2
        //int addressId = data.getInt("addressId");//1
        //String deliveryDate = "2019/01/17";//data.getString("deliveryDate");//2019/01/17 
        //String deliveryTime = "12:40:23";//data.getString("deliveryTime");//12:40:23
        //String deliveryPerson = "Sarvesh";//data.getString("deliveryPerson");//Sarvesh
        //String deliveryAddress = "primary";//data.getString("deliveryAddress");//primary
        //double price = data.getDouble("price");//130.5
        //String status = data.getString("status");//null
        //String comments = "test order booking mobule";//data.getString("comments");//test order booking mobule
        JSONArray itemIdArray = data.getJSONArray("itemIds");//[0, 0];
        JSONArray itemArray = data.getJSONArray("items");//[18, 30];
        JSONArray itemCounts = data.getJSONArray("counts");//[2,5]

        String response = commonUtilities.constructJSON("order", false, "SAM-CRM order booking failed");
        if (vendorId > 0 && itemArray.length() > 0 && itemCounts.length() == itemArray.length()) {// && !deliveryAddress.isEmpty()
            try {
                OrderDataBean orderDataBean = new OrderDataBean();
                orderDataBean.setVendor_id(vendorId);
                orderDataBean.setCustomer_id(customerId);
                //orderDataBean.setDelivery_date(new SimpleDateFormat("yyyy/MM/dd").parse(deliveryDate));
                //orderDataBean.setDelivery_time(deliveryTime);
                //orderDataBean.setDelivery_person(deliveryPerson);
                //orderDataBean.setDelivery_address(deliveryAddress);
                //orderDataBean.setPrice(price);
                //orderDataBean.setStatus(status);
                //orderDataBean.setComments(comments);
                ArrayList<OrderedItemBean> items = new ArrayList<>();
                for (int i = 0; i < itemArray.length(); i++) {
                    OrderedItemBean itemBean = new OrderedItemBean();
                    itemBean.setItem_id((int) itemIdArray.get(i));
                    itemBean.setProduct_id((int) itemArray.get(i));
                    itemBean.setQuantity((int) itemCounts.get(i));
                    items.add(itemBean);
                }
                orderDataBean.setItems(items);
//                CustomerAddressBean addressBean = new CustomerAddressBean();
//                addressBean.setAddresstype(deliveryAddress);
//                if (!deliveryAddress.equalsIgnoreCase("primary")) {
//                    addressBean.setRecepient(deliveryAddress);
//                    addressBean.setAddresstype("secondary");
//                    addressBean.setStreet(data.getString("street"));//Gaurs International school road
//                    addressBean.setCountry(data.getString("country"));//india
//                    addressBean.setZipcode(data.getString("zipcode"));//201308
//                    addressBean.setMobile(Long.valueOf("9312181442"));//9312181442//data.getLong("mobile")
//                }
//                if (addressId == 0) {
//                    orderBookingDataSource.createAddress(orderDataBean.getCustomer_id(), addressBean);
//                    orderDataBean.setCustomerAddress(addressBean);
//                    addressId = addressBean.getAddress_id();
//                }
                orderBookingDataSource.createOrder(orderDataBean);
                int orderedItems = 0;
                if (orderDataBean.getOrder_id() > 0) {
                    orderedItems = orderBookingDataSource.createItems(items, orderDataBean.getOrder_id());
                }
                if (orderedItems > 0) {
                    ArrayList<OrderDataBean> ordersList = new ArrayList<>();
                    ordersList.add(orderDataBean);
                    response = commonUtilities.constructOrdersJSON(ordersList, null, true);
                }

            } catch (Exception e) {
                System.out.println("orderData: exception" + e);
            }
        }
        return response;
    }

    @RequestMapping(value = "/samcrmMakePayment", method = RequestMethod.POST)
    public String makePayment(@RequestBody com.fasterxml.jackson.databind.JsonNode paymentData) {

        JSONObject data = new JSONObject(paymentData.toString());
        PaymentBean paymentBean = new PaymentBean();
        paymentBean.setOrderId(data.getInt("orderId"));//
        paymentBean.setCouponCode(data.getString("couponCode"));//
        paymentBean.setPaymentMode(data.getString("paymentMode"));//cash-on-delivery OR samcrm-pay-balance OR samcrm-borrow-loan
        paymentBean.setGrossTotalPayment(data.getDouble("grossTotalPayment"));//
        paymentBean.setGst(data.getDouble("gst"));//
        paymentBean.setSgst(data.getDouble("sgst"));//
        paymentBean.setServiceCharges(data.getDouble("serviceCharges"));//

        String response = commonUtilities.constructJSON("payment", false, "SAM-CRM payment failed");
        try {
            String transactionNo = orderBookingDataSource.completeTransaction(paymentBean);
            String invoiceNo = orderBookingDataSource.generateInvoice(transactionNo);
            response = commonUtilities.constructJSON("payment", true, "SAM-CRM payment done! Invoice No: " + invoiceNo);
        } catch (Exception e) {
        }
        return response;
    }

    @RequestMapping(value = "/samcrmOrdersList", method = RequestMethod.GET)
    public String getOrdersList(@RequestParam("mobile") String mobile, @RequestParam("customerId") int customerId, @RequestParam("status") String status) {

        ArrayList<OrderDataBean> ordersList = new ArrayList<>();
        String response = commonUtilities.constructOrdersJSON(ordersList, null, false);
        try {
            ordersList = orderBookingDataSource.getOrdersList(mobile, customerId, status);
            if (!ordersList.isEmpty()) {
                response = commonUtilities.constructOrdersJSON(ordersList, null, true);
            }
        } catch (Exception e) {
        }
        return response;
    }

    @RequestMapping(value = "/samcrmCheckInventoryAndUpdateQuantity", method = RequestMethod.GET)
    public String checkInventoryAndUpdateQuantity(@RequestParam("productId") int productId, @RequestParam("orderId") int orderId, @RequestParam("quantity") int quantity, @RequestParam("unit") String unit, @RequestParam("itemId") int itemId) {

        String response = "";
        try {
            int inventoryQuantity = 0;
            if (productId > 0) {
                inventoryQuantity = vendorDataSource.checkInventory(productId);
            }
            if (inventoryQuantity > quantity && orderId > 0) {
                int record = orderBookingDataSource.updateCartItemQuantity(itemId, quantity, orderId);
                if (record == 1) {
                    response = commonUtilities.constructJSON("update", true, "quantity updated successfully.");
                } else {
                    response = commonUtilities.constructJSON("update", false, "Inventory is " + inventoryQuantity + " only");
                }
            }
        } catch (Exception e) {
        }
        return response;
    }

    @RequestMapping(value = "/samcrmDiscountCoupon", method = RequestMethod.GET)
    public String getDiscountCoupon(@RequestParam("vendor_id") int vendor_id, @RequestParam("customer_id") int customer_id, @RequestParam("order_id") int order_id, @RequestParam("total_price") double total_price) {

        int discount;
        String couponCode = null;//V4C0O0INR10
        String response = commonUtilities.constructJSON("0", false, couponCode);
        try {
            discount = vendorDataSource.getDiscount(vendor_id, customer_id, total_price);
            if (discount > 0) {
                couponCode = vendorDataSource.generateCouponCode(order_id, vendor_id, customer_id, discount);
                if (couponCode != null && !couponCode.isEmpty()) {
                    response = commonUtilities.constructJSON(String.valueOf(discount), true, couponCode);
                    UserDataBean bean = userDataSource.selectUserData(customer_id);
                    String message = "Congratulations! The generated discount coupon code is " + couponCode;
                    //SMSUtils.sendSamcrmOtp(bean.getMobile(), message);
                }
            }
        } catch (Exception e) {
        }
        return response;
    }

    @RequestMapping(value = "/samcrmConfirmOrder", method = RequestMethod.GET)
    public String confirmOrder(@RequestParam("vendor_id") int vendor_id, @RequestParam("customer_id") int customer_id, @RequestParam("order_id") int order_id, @RequestParam("couponCode") String couponCode, @RequestParam("total_price") double total_price) {

        String response = commonUtilities.constructJSON("Confirm Order", false);
        try {
            boolean confirmationStatus = orderBookingDataSource.confirmOrder(vendor_id, customer_id, order_id, couponCode, total_price);
            if (confirmationStatus) {
                response = commonUtilities.constructJSON("Confirm Order", true);
                UserDataBean userData = userDataSource.selectUserData(customer_id);
                if (userData != null) {
                    //SMSUtils.sendSMS(userData.getMobile(), "Thanks for confirming the order! Happy to help / serve you!");
                }
            }
        } catch (Exception e) {
        }
        return response;
    }

    @RequestMapping(value = "/samcrmUpdateOrderDelivery", method = RequestMethod.GET)
    public String updateOrderDelivery(@RequestParam("order_id") int order_id, @RequestParam("delivery_person_id") int delivery_person_id, @RequestParam("recepient") String recepient, @RequestParam("contact_no") long contact_no) {

        String response = commonUtilities.constructJSON("Close Order", false);
        try {
            int customer_id = orderBookingDataSource.updateOrderDelivery(order_id, delivery_person_id, recepient, contact_no);
            if (customer_id > 0) {
                vendorDataSource.updateInventoryByOrderId(order_id);
                response = commonUtilities.constructJSON("Close Order", true);
                UserDataBean userData = userDataSource.selectUserData(customer_id);
                if (userData != null) {
                    //SMSUtils.sendSMS(userData.getMobile(), "The order is delivered! Allow me to fulfil more orders!");
                }
            }
        } catch (Exception e) {
        }
        return response;
    }

    @RequestMapping(value = "/samcrmNewAddress", method = RequestMethod.POST)
    public String createNewAddress(@RequestBody com.fasterxml.jackson.databind.JsonNode addressData) {

        JSONObject data = new JSONObject(addressData.toString());
        int vendorId = data.getInt("vendorId");//1
        int customerId = data.getInt("customerId");//9
        int orderId = data.getInt("orderId");//1

        CustomerAddressBean addressBean = new CustomerAddressBean();
        addressBean.setAddresstype("secondary");
        addressBean.setRecepient(data.getString("recepient"));
        addressBean.setStreet(data.getString("street"));
        addressBean.setCity(data.getString("city"));
        addressBean.setState(data.getString("state"));
        addressBean.setCountry(data.getString("country"));
        addressBean.setZipcode(data.getString("zipcode"));
        addressBean.setMobile(data.getLong("contact_no"));

        String response = commonUtilities.constructJSON("0", false, "New delivery address not added.");
        try {
            int addressId = orderBookingDataSource.createNewAddress(vendorId, customerId, orderId, addressBean);
            if (addressId != 0) {
                response = commonUtilities.constructJSON(String.valueOf(addressId), true, "New delivery address added.");
            }
        } catch (Exception e) {
        }
        return response;
    }

    @RequestMapping(value = "/samcrmDeleteItem", method = RequestMethod.GET)
    public String removeItem(@RequestParam("vendor_id") int vendor_id, @RequestParam("customer_id") int customer_id, @RequestParam("order_id") int order_id, @RequestParam("item_id") int item_id) {

        String response = commonUtilities.constructJSON("Remove Item from cart.", false);
        try {
            int record = orderBookingDataSource.removeItem(order_id, vendor_id, customer_id, item_id);
            if (record == 1) {
                response = commonUtilities.constructJSON("Remove Item from cart.", true);
            }
        } catch (Exception e) {
        }
        return response;
    }

    @RequestMapping(value = "/samcrmCancelOrder", method = RequestMethod.GET)
    public String cancelOrder(@RequestParam("order_id") int order_id, @RequestParam("comment") String comment, @RequestParam("delivery_person_id") int delivery_person_id) {

        String response = commonUtilities.constructJSON("Order Cancellation Status", false);
        try {
            int cancellationStatus = orderBookingDataSource.cancelOrder(order_id, comment, delivery_person_id);
            if (cancellationStatus > 0) {
                response = commonUtilities.constructJSON("Order Cancellation Status", true);
                ArrayList<OrderDataBean> ordersList = orderBookingDataSource.getOrdersList(null, order_id, "cancelled");
                OrderDataBean bean = ordersList.get(0);
                UserDataBean vendorData = userDataSource.selectUserData(bean.getVendor_id());
                UserDataBean customerData = userDataSource.selectUserData(bean.getCustomer_id());

                String subject = ":: Order Cancelled";
                String followupDetails = "Hello " + customerData.getVendorTitle() + " " + customerData.getName();
                followupDetails += "\n\nOrder Cancellation Comment:\n" + bean.getComments() + "\n Thanks.'\n\n";
                followupDetails += "From: \n" + vendorData.getName() + "\nAddress: " + vendorData.getAddress() + "\nContact No: " + vendorData.getMobile();

                emailUtility.pushSalesAndServicesViaEmail(vendorData.getName(), vendorData.getEmail(), customerData.getEmail(), subject, followupDetails, customerData.getName());
                //SMSUtils.sendSamcrmOtp(customerData.getMobile(), followupDetails);
            }
        } catch (Exception e) {
        }
        return response;
    }

    @RequestMapping(value = "/samcrmUpdateDeliveryStatus", method = RequestMethod.GET)
    public String updateDeliveryStatus(@RequestParam("order_id") int order_id, @RequestParam("delivery_person_id") int delivery_person_id, @RequestParam("step") int step) {

        String response = commonUtilities.constructJSON("Update Delivery Status", false);
        try {
            boolean deliveryStatus = orderBookingDataSource.updateDeliveryStatus(order_id, delivery_person_id, step);
            if (deliveryStatus) {
                response = commonUtilities.constructJSON("Update Delivery Status", deliveryStatus);
            }
        } catch (Exception e) {
        }
        return response;
    }

    @RequestMapping(value = "/samcrmTrackOrder", method = RequestMethod.GET)
    public String trackOrder(@RequestParam("order_id") int order_id, @RequestParam("customer_id") int customer_id) {

        String response = commonUtilities.constructJSONString(null, false, "Order Tracker Failed");
        try {
            ArrayList<OrderTrackerBean> list = orderBookingDataSource.getOrderDeliveryStatus(order_id, customer_id);
            response = commonUtilities.constructJSONString(list, true, "Order Tracker Success");
        } catch (Exception e) {
        }
        return response;
    }

    @RequestMapping(value = "/samcrmUrgentService", method = RequestMethod.GET)
    public String urgentService(@RequestParam("vendorId") int vendor_id, @RequestParam("name") String name, @RequestParam("mobile") long mobile, @RequestParam("address") String address, @RequestParam("request") String request) {
        String response = commonUtilities.constructJSON("Urgent - Service", false);
        try {
            ArrayList<OrderDataBean> ordersList = orderBookingDataSource.getOrdersList(String.valueOf(mobile), vendor_id, "open");

            if (vendor_id > 0 && ordersList.isEmpty()) {
                long customerId = vendorDataSource.checkUserExistance(mobile);
                String contactNo = vendorDataSource.getUrgentServiceContactNo(vendor_id);
                if (!contactNo.isEmpty()) {

                    OrderDataBean orderDataBean = new OrderDataBean();
                    orderDataBean.setVendor_id(vendor_id);
                    orderDataBean.setCustomer_id(customerId);
                    orderDataBean.setDelivery_address(address);
                    orderDataBean.setComments(request);
                    String otpText = "";
                    int otpNumber = 1111;
                    if (customerId == 0) {
                        Random random = new Random();
                        int max = 9999, min = 1000;
                        otpNumber = random.nextInt(max - min + 1) + min;
                        otpText = otpNumber + " is One Time Password for SAM-CRM urgent service authentication. \nKindly don't share this with others. \n Vendor will ask you this OTP before initiating service.";
                        String thanks = "\n Thanks. For further inquiry please call : " + contactNo;
                        otpText = otpText + thanks;
                        customerId = vendorDataSource.addContact(name, "info@raagatech.com", String.valueOf(mobile), "", address, "", request, String.valueOf(vendor_id), "0", thanks);
                        orderDataBean.setCustomer_id(customerId);
                    }
                    orderDataBean.setCouponCode(String.valueOf(otpNumber));
                    orderBookingDataSource.createOrder(orderDataBean);
                    if (otpText != null && !otpText.isEmpty()) {
                        //SMSUtils.sendSamcrmOtp(String.valueOf(mobile), otpText);
                    }
                }
                response = commonUtilities.constructJSON("Urgent - Service", true);
            } else {
                response = commonUtilities.constructJSON("First, confirm OR cancel the OPEN order then use Urgent - Service. Thanks!", false);
            }
        } catch (Exception e) {
        }

        return response;
    }
}
