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
import java.util.Date;
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
            String gender, String postalAddress, String pincode, int inspiratorId) throws Exception {

        boolean insertStatus = Boolean.FALSE;
        // With AutoCloseable, the connection is closed automatically.
        int id = oracleDataSource.generateNextPrimaryKey("raagatech_user", "user_id");
        try ( OracleConnection connection = (OracleConnection) oracleDataSource.getOracleDataSource().getConnection()) {
            String queryInsertUser = "INSERT into raagatech_user (user_id, username, password, creation_date, email, country_code, mobile,"
                    + "gender, postalAddress, pincode, inspirator_id) "
                    + "VALUES (" + id + ", '" + username + "','" + password + "',?, '" + email + "', 091, " + mobileNo + ", '" + gender
                    + "', '" + postalAddress + "', " + pincode + ", " + inspiratorId + ")";
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
            String inspiration, String comfortability, String primaryskill, int userId, int pinCode, int examSession) throws Exception {
        boolean insertStatus = Boolean.FALSE;
        // With AutoCloseable, the connection is closed automatically.
        try ( OracleConnection connection = (OracleConnection) oracleDataSource.getOracleDataSource().getConnection()) {
            char sex = gender.equals("Male") ? 'M' : 'F';
            int inquiry_id = oracleDataSource.generateNextPrimaryKey("raagatech_inquiry", "inquiry_id");
            String queryInsertInquiry = "INSERT into raagatech_inquiry (inquiry_id, firstname, inspiration_id, inquiry_date, email, mobile"
                    + ", level_id, address_line1, nationality, father_name, mother_name, date_of_birth, telephone, photo"
                    + ", gender, inspiration, comfortability, primaryskill, user_id, pincode, exam_session) "
                    + "VALUES (" + inquiry_id + ", '" + inquiryname + "'," + inspirationid + ",?, '" + email + "', " + mobileNo + ","
                    + levelid + ", '" + address + "', '" + nationality + "', '" + fname + "', '" + mname + "', ?, " + telOther + ", '" + image + "', '"
                    + sex + "', '" + inspiration + "', '" + comfortability + "', '" + primaryskill + "', " + userId + ", " + pinCode + ", " + examSession + ")";
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
    public ArrayList<InquiryBean> listInquiry(int userId, int inspiratorId) throws Exception {
        ArrayList<InquiryBean> inquiryList = new ArrayList<>();
        InquiryBean inquiry;
        // With AutoCloseable, the connection is closed automatically.
        try ( OracleConnection connection = (OracleConnection) oracleDataSource.getOracleDataSource().getConnection()) {
            String querySelectInquiries = "SELECT * FROM raagatech_inquiry"
                    + " WHERE exam_session = " + Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date()))
                    +" AND user_id = " + userId + " OR user_id in (select user_id from raagatech_user where inspirator_id = "+userId+")";
            
            if(inspiratorId > 0) {
                querySelectInquiries = "SELECT * FROM raagatech_inquiry"
                    + " WHERE exam_session = " + Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date())) 
                    + " AND user_id in (select user_id from raagatech_user where inspirator_id = "+inspiratorId+")";
            }
            PreparedStatement statement = connection.prepareStatement(querySelectInquiries);
            ResultSet record = statement.executeQuery();
            while (record.next()) {
                inquiry = new InquiryBean();
                inquiry.setFirstname(record.getString("firstname"));
                inquiry.setGender(record.getString("gender").equals("M") ? 'M' : 'F');
                inquiry.setEmail(record.getString("email"));
                inquiry.setInquiry_date(record.getDate("inquiry_date"));
                inquiry.setNationality(record.getString("nationality"));
                inquiry.setMobile(record.getLong("mobile"));
                inquiry.setInquiry_id(record.getInt("inquiry_id"));
                inquiry.setExamSession(record.getInt("exam_session"));
                inquiry.setPrimaryskill(record.getString("primaryskill"));
                inquiryList.add(inquiry);
            }
        }
        return inquiryList;
    }

    @Override
    public boolean updateInquiry(int inquiry_id, String inquiryname, int inspirationid, String email, long mobileNo,
            int levelid, String address, String followupDetails, String nationality, String fname, String mname,
            String dob, long telOther, String image, String gender, String inspiration,
            String comfortability, String primaryskill, int userId, int pinCode, int examSession) throws Exception {
        boolean updateStatus = Boolean.FALSE;
        // With AutoCloseable, the connection is closed automatically.
        try ( OracleConnection connection = (OracleConnection) oracleDataSource.getOracleDataSource().getConnection()) {
            char sex = gender.equals("Male") ? 'M' : 'F';
            String queryUpdateInquiry = "UPDATE raagatech_inquiry set firstname = '" + inquiryname + "', inspiration_id = " + inspirationid + ", email = '" + email 
                    + "', mobile = "+ mobileNo + ", level_id = " + levelid + ", address_line1 = '" + address + "', gender = '"+ sex + "',"
                    + " pincode = " + pinCode + ", exam_session = " + examSession+ ", primaryskill = '" + primaryskill + "'"  
                    + " WHERE inquiry_id = " + inquiry_id + " AND user_id = " + userId;
            PreparedStatement statement = connection.prepareStatement(queryUpdateInquiry);
            int records = statement.executeUpdate();
            if (records > 0) {
                String queryUpdateFollowupDetails = "UPDATE raagatech_followupdetails set followup_details = '" + followupDetails + "' "
                        + "WHERE inquiry_id = "+inquiry_id;
                PreparedStatement statement2 = connection.prepareStatement(queryUpdateFollowupDetails);
                records = statement2.executeUpdate();
                if (records > 0) {
                    updateStatus = Boolean.TRUE;
                }
            }
        }
        return updateStatus;
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
        InquiryBean inquiry = null;
        // With AutoCloseable, the connection is closed automatically.
        try ( OracleConnection connection = (OracleConnection) oracleDataSource.getOracleDataSource().getConnection()) {
            String queryInsertUser = "SELECT ri.*, rf.followup_details FROM raagatech_inquiry ri join raagatech_followupdetails rf on ri.inquiry_id = rf.inquiry_id WHERE ri.inquiry_id = " + inquiryId;
            PreparedStatement statement = connection.prepareStatement(queryInsertUser);
            ResultSet record = statement.executeQuery();
            while (record.next()) {
                inquiry = new InquiryBean();
                inquiry.setFirstname(record.getString("firstname"));
                inquiry.setGender(record.getString("gender").equals("M") ? 'M' : 'F');
                inquiry.setEmail(record.getString("email"));
                inquiry.setInquiry_date(record.getDate("inquiry_date"));
                inquiry.setNationality(record.getString("nationality"));
                inquiry.setMobile(record.getLong("mobile"));
                inquiry.setInquiry_id(record.getInt("inquiry_id"));
                inquiry.setInspiration_id(record.getInt("inspiration_id"));
                inquiry.setLevel_id(record.getInt("level_id"));
                inquiry.setPincode(record.getInt("pincode"));
                inquiry.setExamSession(record.getInt("exam_session"));
                inquiry.setAddress_line1(record.getString("address_line1"));
                inquiry.setFollowup_details(record.getString("followup_details"));
                inquiry.setPrimaryskill(record.getString("primaryskill"));

            }
        }
        return inquiry;

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
                userData.setPincode(record.getInt("pincode"));
                userData.setInspiratorId(record.getInt("inspirator_id"));
                userData.setGender(record.getString("gender").equals("M") ? 'M' : 'F');
                userData.setAddress(record.getString("postaladdress"));
            }
        }
        return userData;
    }

    @Override
    public boolean updateUserData(String username, String password, long mobileNo, 
            String gender, String postalAddress, String pincode, int userId, int inspiratorId) throws Exception {
        boolean updateStatus = Boolean.FALSE;
        // With AutoCloseable, the connection is closed automatically.
        try ( OracleConnection connection = (OracleConnection) oracleDataSource.getOracleDataSource().getConnection()) {
            char sex = gender.equals("Male") ? 'M' : 'F';
            String queryUpdateUser = "UPDATE raagatech_user set username = '" + username + "', password = '" + password + "'"
                    + ", mobile = "+ mobileNo + ", gender = '"+ sex + "', pincode = " + pincode + ""
                    + ", inspirator_id = " + inspiratorId +", postaladdress = '" + postalAddress + "'"
                    + " WHERE user_id = " + userId;
            PreparedStatement statement = connection.prepareStatement(queryUpdateUser);
            int records = statement.executeUpdate();
            if(records > 0) {
                updateStatus = Boolean.TRUE;
            }
        }
        return updateStatus;
    }

    @Override
    public ArrayList<UserDataBean> getUsersList(String username, String password) throws Exception {
        ArrayList<UserDataBean> usersList = new ArrayList<>();
        UserDataBean userData;
        // With AutoCloseable, the connection is closed automatically.
        try ( OracleConnection connection = (OracleConnection) oracleDataSource.getOracleDataSource().getConnection()) {
            String querySelectUser = "SELECT * FROM raagatech_user WHERE (email = '" + username + "' "
                    + "OR username = '" + username + "') AND password = '" + password + "' AND emailverification = 1";
            PreparedStatement statement = connection.prepareStatement(querySelectUser);
            ResultSet record = statement.executeQuery();
            while (record.next()) {
                userData = new UserDataBean();
                userData.setUserName(record.getString("username"));
                userData.setEmail(record.getString("email"));
                userData.setMobile(record.getLong("mobile"));
                userData.setUserId(record.getInt("user_id"));
                userData.setGender(record.getString("gender").equals("M") ? 'M' : 'F');
                usersList.add(userData);
            }
        }
        return usersList;
    }

    @Override
    public ArrayList<UserDataBean> listOverAllContacts() throws Exception {
        
        ArrayList<UserDataBean> contactsList = new ArrayList<>();
        UserDataBean userData;
        // With AutoCloseable, the connection is closed automatically.
        try ( OracleConnection connection = (OracleConnection) oracleDataSource.getOracleDataSource().getConnection()) {
        String querySelectUser = "select DISTINCT ru.USERNAME as name, ru.EMAIL as email from RAAGATECH_USER ru"
                + " UNION"
                + " select DISTINCT ri.FIRSTNAME as name, ri.EMAIL as email from RAAGATECH_INQUIRY ri";
        PreparedStatement statement = connection.prepareStatement(querySelectUser);
            ResultSet record = statement.executeQuery();
            while (record.next()) {
                userData = new UserDataBean();
                userData.setUserName(record.getString("name"));
                userData.setEmail(record.getString("email"));
                contactsList.add(userData);
            }
        }
        return contactsList;
    }
    
    @Override
    public UserDataBean getUserData(int userId) throws Exception {
        UserDataBean userData = null;
        // With AutoCloseable, the connection is closed automatically.
        try ( OracleConnection connection = (OracleConnection) oracleDataSource.getOracleDataSource().getConnection()) {
            String queryInsertUser = "SELECT * FROM raagatech_user WHERE user_id = " + userId + " AND emailverification = 1";
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
                userData.setPincode(record.getInt("pincode"));
                userData.setInspiratorId(record.getInt("inspirator_id"));
                userData.setGender(record.getString("gender").equals("M") ? 'M' : 'F');
                userData.setAddress(record.getString("postaladdress"));
            }
        }
        return userData;
    }
}
