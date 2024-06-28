/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.raagatech.omp.musicapp;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Date;

/**
 *
 * @author sarve
 */
public interface RaagatechMusicDataSourceInterface {

    public UserDataBean getUserData(String username, String password) throws Exception;
    
    public int insertUser(String username, String password, String email, long mobileNo, 
            String gender, String postalAddress, String pincode, int inspirator_id) throws Exception;
    
    public boolean updateUserForEmailVerification(int userId, String email) throws Exception;

    public boolean updateUserForMobileVerification(int userId, String email, long mobileNo) throws Exception;

    public boolean insertInquiry(String inquiryname, int inspirationid, String email, long mobileNo,
            int levelid, String address, String followupDetails,
            String nationality, String fname, String mname, String dob, long telOther, String image,
            String gender, String inspiration, String comfortability, String primaryskill,
            int userId, int pinCode, int examSession) throws Exception;

    public LinkedHashMap<Integer, String> selectLevel() throws Exception;

    public LinkedHashMap<Integer, String> selectInspiration() throws Exception;

    public ArrayList<InquiryBean> listInquiry(int userId, int inspiratorId) throws Exception;

    public boolean updateInquiry(int inquiry_id, String inquiryname, int inspirationid, String email, long mobileNo,
            int levelid, String address, String followupDetails, String nationality, String fname, String mname, String dob, long telOther, String image,
            String gender, String inspiration, String comfortability, String primaryskill
            , int userId, int pinCode, int examSession) throws Exception;

    public LinkedHashMap<Integer, String> selectInquiryStatus() throws Exception;

    public boolean updateFollowup(int inquiry_id, int inquirystatus_id, String followupDetails) throws Exception;

    public InquiryBean getInquiryById(int inquiryId) throws Exception;

    public int generateNextPrimaryKey(String tableName, String columnName) throws Exception;

    public ArrayList<SliderImageBean> listSliderImage();

    public int selectInquiry(String email, long mobileNo) throws Exception;

    public InquiryBean getInquiryDetails(String email, long mobileNo) throws Exception;

    public boolean updateUserData(String username, String password, long mobileNo, 
            String gender, String postalAddress, String pincode, int userId, int inspiratorId) throws Exception;

    public ArrayList<UserDataBean> getUsersList(String username, String password) throws Exception;

    public ArrayList<UserDataBean> listOverAllContacts(Date dob) throws Exception;
    
    public UserDataBean getUserData(int userId) throws Exception;
}
