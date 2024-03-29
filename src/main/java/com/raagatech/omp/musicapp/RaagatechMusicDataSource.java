/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.raagatech.omp.musicapp;

import com.raagatech.common.datasource.OracleDatabaseInterface;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import oracle.jdbc.OracleConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author sarve
 */
@Service
public class RaagatechMusicDataSource implements RaagatechMusicDataSourceInterface {

    protected static final SimpleDateFormat FORMATTER = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");

    @Autowired
    private OracleDatabaseInterface oracleDataSource;

    @Override
    public boolean insertUser(String username, String password, String email, long mobileNo,
            String gender, String postalAddress, String pincode) throws Exception {

        boolean insertStatus = Boolean.FALSE;
        // With AutoCloseable, the connection is closed automatically.
        int id = oracleDataSource.generateNextPrimaryKey("raagatech_user", "user_id");
        try ( OracleConnection connection = (OracleConnection) oracleDataSource.getOracleDataSource().getConnection()) {
            String queryInsertUser = "INSERT into raagatech_user (user_id, username, password, creation_date, email, country_code, mobile,"
                    + "gender, postalAddress, pincode) "
                    + "VALUES (" + id + ", '" + username + "','" + password + "',?, '" + email + "', 091, " + mobileNo + ", '" + gender
                    + "', '" + postalAddress + "', '" + pincode + "')";
            PreparedStatement statement = connection.prepareStatement(queryInsertUser);
            statement.setTimestamp(1, getCurrentTimeStamp());
            int records = statement.executeUpdate();
            if (records > 0) {
                insertStatus = Boolean.TRUE;
            }
        }
        return insertStatus;
    }

    @Override
    public boolean updateUserForEmailVerification(String email) throws Exception {
        boolean updateStatus = Boolean.FALSE;
        // With AutoCloseable, the connection is closed automatically.
        try ( OracleConnection connection = (OracleConnection) oracleDataSource.getOracleDataSource().getConnection()) {
            String queryUpdateUser = "update raagatech_user set emailverification = 1 where email = '" + email + "'";
            PreparedStatement statement = connection.prepareStatement(queryUpdateUser);
            int records = statement.executeUpdate();
            if (records > 0) {
                updateStatus = Boolean.TRUE;
            }
        }
        return updateStatus;
    }

    @Override
    public boolean insertInquiry(String inquiryname, int inspirationid, String email, long mobileNo,
            int levelid, String address, String followupDetails, String nationality,
            String fname, String mname, String dob, long telOther, String image, String gender,
            String inspiration, String comfortability, String primaryskill, int userId) throws Exception {
        boolean insertStatus = Boolean.FALSE;
        // With AutoCloseable, the connection is closed automatically.
        try ( OracleConnection connection = (OracleConnection) oracleDataSource.getOracleDataSource().getConnection()) {
            char sex = gender.equals("Male")? 'M':'F';
            int inquiry_id = oracleDataSource.generateNextPrimaryKey("raagatech_inquiry", "inquiry_id");
            String queryInsertInquiry = "INSERT into raagatech_inquiry (inquiry_id, firstname, inspiration_id, inquiry_date, email, mobile"
                    + ", level_id, address_line1, nationality, father_name, mother_name, date_of_birth, telephone, photo, gender, inspiration, comfortability, primaryskill, user_id) "
                    + "VALUES (" + inquiry_id + ", '" + inquiryname + "'," + inspirationid + ",?, '" + email + "', " + mobileNo + ","
                    + levelid + ", '" + address + "', '" + nationality + "', '" + fname + "', '" + mname + "', ?, " + telOther + ", '" + image + "', '"
                    + sex + "', '" + inspiration + "', '" + comfortability + "', '" + primaryskill + "', " + userId + ")";
            PreparedStatement statement = connection.prepareStatement(queryInsertInquiry);
            statement.setTimestamp(1, getCurrentTimeStamp());
            statement.setTimestamp(2, getCurrentTimeStamp());
            int records = statement.executeUpdate();
            if (records > 0) {
                int followup_id = oracleDataSource.generateNextPrimaryKey("raagatech_followupdetails", "followup_id");
                String queryInsertFollowupDetails = "INSERT into raagatech_followupdetails (followup_id, inquiry_id, inquirystatus_id, followup_details, followup_date) "
                        + "VALUES (" + followup_id + ", " + inquiry_id + ", 1, '" + followupDetails + "',?)";
                PreparedStatement statement2 = connection.prepareStatement(queryInsertFollowupDetails);
                statement2.setTimestamp(1, getCurrentTimeStamp());
                records = statement2.executeUpdate();
                if (records > 0) {
                    insertStatus = Boolean.TRUE;
                }
            }
        }
        return insertStatus;
    }

    @Override
    public LinkedHashMap<Integer, String> selectLevel() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public LinkedHashMap<Integer, String> selectInspiration() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ArrayList<InquiryBean> listInquiry(int userId) throws Exception {
        ArrayList<InquiryBean> inquiryList = new ArrayList<>();
        InquiryBean inquiry;
        // With AutoCloseable, the connection is closed automatically.
        try ( OracleConnection connection = (OracleConnection) oracleDataSource.getOracleDataSource().getConnection()) {
            String queryInsertUser = "SELECT * FROM raagatech_inquiry WHERE user_id = " + userId;
            PreparedStatement statement = connection.prepareStatement(queryInsertUser);
            ResultSet record = statement.executeQuery();
            while (record.next()) {
                inquiry = new InquiryBean();
                inquiry.setFirstname(record.getString("firstname"));
                inquiry.setGender(record.getString("gender").equals("M")?'M':'F');
                inquiry.setEmail(record.getString("email"));
                inquiry.setInquiry_date(record.getDate("inquiry_date"));
                inquiry.setNationality(record.getString("nationality"));
                inquiry.setMobile(record.getLong("mobile"));
                inquiry.setInquiry_id(record.getInt("inquiry_id"));
                inquiryList.add(inquiry);
            }
        }
        return inquiryList;
    }

    @Override
    public boolean updateInquiry(int inquiry_id, String inquiryname, int inspirationid, String email, long mobileNo, int levelid, String address, String followupDetails, String nationality, String fname, String mname, String dob, long telOther, String image, String gender, String inspiration, String comfortability, String primaryskill) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public LinkedHashMap<Integer, String> selectInquiryStatus() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean updateFollowup(int inquiry_id, int inquirystatus_id, String followupDetails) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public InquiryBean getInquiryById(int inquiryId) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int generateNextPrimaryKey(String tableName, String columnName) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ArrayList<SliderImageBean> listSliderImage() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int selectInquiry(String email, long mobileNo) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public InquiryBean getInquiryDetails(String email, long mobileNo) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private java.sql.Timestamp getCurrentTimeStamp() {

        java.util.Date today = new java.util.Date();
        return new java.sql.Timestamp(today.getTime());

    }

    @Override
    public UserDataBean getUserData(String username, String password) throws Exception {
        UserDataBean userData = null;
        // With AutoCloseable, the connection is closed automatically.
        try ( OracleConnection connection = (OracleConnection) oracleDataSource.getOracleDataSource().getConnection()) {
            String queryInsertUser = "SELECT * FROM raagatech_user WHERE (email = '" + username + "' "
                    + "OR username = '" + username + "') AND password = '" + password + "' AND emailverification = 1";
            PreparedStatement statement = connection.prepareStatement(queryInsertUser);
            ResultSet record = statement.executeQuery();
            while (record.next()) {
                userData = new UserDataBean();
                userData.setUserName(record.getString("username"));
                userData.setPassword(record.getString("password"));
                userData.setEmail(record.getString("email"));
                userData.setCreationDate(record.getDate("creation_date"));
                userData.setCountryCode(record.getInt("country_code"));
                userData.setMobile(record.getLong("mobile"));
                userData.setUserId(record.getInt("user_id"));
            }
        }
        return userData;
    }
}
