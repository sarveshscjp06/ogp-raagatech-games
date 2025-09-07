/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.raagatech.omp.musicapp;

import com.raagatech.omp.musicbean.UserDataBean;
import com.raagatech.omp.musicbean.SliderImageBean;
import com.raagatech.omp.musicbean.PssExamReportBean;
import com.raagatech.omp.musicbean.InquiryBean;
import com.raagatech.Feedback;
import com.raagatech.common.datasource.OracleDatabaseInterface;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import oracle.jdbc.OracleConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author sarve
 */
@Service
public class RaagatechMusicDataSource implements RaagatechMusicDataSourceInterface {

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

    @Autowired
    private OracleDatabaseInterface oracleDataSource;

    @Override
    public int insertUser(String username, String password, String email, long mobileNo,
            String gender, String postalAddress, String pincode, int inspiratorId, int discount) throws Exception {

        // With AutoCloseable, the connection is closed automatically.
        int id = oracleDataSource.generateNextPrimaryKey("raagatech_user", "user_id");
        try ( OracleConnection connection = (OracleConnection) oracleDataSource.getOracleDataSource().getConnection()) {
            char sex = gender.equals("Male") ? 'M' : 'F';
            String queryInsertUser = "INSERT into raagatech_user (user_id, username, password, creation_date, email, country_code, mobile,"
                    + "gender, postalAddress, pincode, inspirator_id) "
                    + "VALUES (" + id + ", '" + username + "','" + password + "',?, '" + email + "', 091, " + mobileNo + ", '" + sex
                    + "', '" + postalAddress + "', " + pincode + ", " + inspiratorId + ")";
            PreparedStatement statement = connection.prepareStatement(queryInsertUser);
            statement.setTimestamp(1, getCurrentTimeStamp());
            int records = statement.executeUpdate();
            if (records == 0) {
                id = 0;
            } else {
                String queryUpdateInquiry = "UPDATE raagatech_inquiry set user_id = " + id
                        + " WHERE email = '" + email + "' AND mobileNo = " + mobileNo;
                statement = connection.prepareStatement(queryUpdateInquiry);
                statement.executeUpdate();
            }
        }
        return id;
    }

    @Override
    public boolean updateUserForEmailVerification(int userId, String email) throws Exception {
        boolean updateStatus = Boolean.FALSE;
        // With AutoCloseable, the connection is closed automatically.
        try ( OracleConnection connection = (OracleConnection) oracleDataSource.getOracleDataSource().getConnection()) {
            String queryUpdateUser = "update raagatech_user set emailverification = 1 where user_id = " + userId + " AND email = '" + email + "'";
            PreparedStatement statement = connection.prepareStatement(queryUpdateUser);
            int records = statement.executeUpdate();
            if (records > 0) {
                updateStatus = Boolean.TRUE;
            }
            String queryUpdateInquiry = "update raagatech_inquiry set user_id = " + userId + " where email = '" + email + "'";
            statement = connection.prepareStatement(queryUpdateInquiry);
            statement.executeUpdate();
        }
        return updateStatus;
    }

    @Override
    public boolean insertInquiry(String inquiryname, int inspirationid, String email, long mobileNo,
            int levelid, String address, String followupDetails, String nationality,
            String dob, long telOther, String image, String gender,
            int inspiratorId, String comfortability, String primaryskill, int userId, int pinCode,
            String examSession, String fatherName, String motherName, int examFees, int inquiryStatusId) throws Exception {
        boolean insertStatus = Boolean.FALSE;
        // With AutoCloseable, the connection is closed automatically.
        try ( OracleConnection connection = (OracleConnection) oracleDataSource.getOracleDataSource().getConnection()) {
            char sex = gender.equals("Male") ? 'M' : 'F';
            if (address == null || address.isEmpty()) {
                address = "Postal address here...";
                levelid = 1;
                inspiratorId = 1;
                inspirationid = 1;
            }
            int inquiry_id = oracleDataSource.generateNextPrimaryKey("raagatech_inquiry", "inquiry_id");
            String queryInsertInquiry = "INSERT into raagatech_inquiry (inquiry_id, firstname, inspiration_id, inquiry_date, email, mobile"
                    + ", level_id, address_line1, nationality, date_of_birth, photo"
                    + ", gender, inspirator_id, comfortability, primaryskill, user_id, pincode, exam_session, father_name, mother_name, exam_fees) "
                    + "VALUES (" + inquiry_id + ", '" + inquiryname + "'," + inspirationid + ",?, '" + email + "', " + mobileNo + ","
                    + levelid + ", '" + address + "', '" + nationality + "', to_date(?, 'dd-mm-yyyy'), '" + image + "', '"
                    + sex + "', " + inspiratorId + ", '" + comfortability + "', '" + primaryskill + "', " + userId + ", " + pinCode + ", '" + examSession + "'"
                    + ", '" + fatherName + "', '" + motherName + "', " + examFees + ")";
            PreparedStatement statement = connection.prepareStatement(queryInsertInquiry);
            statement.setTimestamp(1, getCurrentTimeStamp());
            statement.setString(2, dob);
            int records = statement.executeUpdate();
            if (records > 0) {
                int followup_id = oracleDataSource.generateNextPrimaryKey("raagatech_followupdetails", "followup_id");
                String queryInsertFollowupDetails = "INSERT into raagatech_followupdetails (followup_id, inquiry_id, inquirystatus_id, followup_details, followup_date) "
                        + "VALUES (" + followup_id + ", " + inquiry_id + ", " + inquiryStatusId + ", '" + followupDetails + "',?)";
                PreparedStatement statement2 = connection.prepareStatement(queryInsertFollowupDetails);
                statement2.setTimestamp(1, getCurrentTimeStamp());
                records = statement2.executeUpdate();
                if (records > 0) {
                    insertStatus = Boolean.TRUE;
                }
                String selectUserQuery = "select user_id from raagatech_user WHERE email = '" + email + "' AND mobile = " + mobileNo;
                statement = connection.prepareStatement(selectUserQuery);
                ResultSet rs = statement.executeQuery();
                int existingUserId = 0;
                while (rs.next()) {
                    existingUserId = rs.getInt("user_id");
                }
                if (existingUserId > 0) {
                    String queryUpdateInquiry = "UPDATE raagatech_inquiry set user_id = " + existingUserId
                            + " WHERE email = '" + email + "' AND mobileNo = " + mobileNo;
                    statement = connection.prepareStatement(queryUpdateInquiry);
                    statement.executeUpdate();
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
    public ArrayList<InquiryBean> listInquiry(int userId, int inspiratorId, String examSession, int inquiryStatusId) throws Exception {
        ArrayList<InquiryBean> inquiryList = new ArrayList<>();
        InquiryBean inquiry;
        // With AutoCloseable, the connection is closed automatically.
        try ( OracleConnection connection = (OracleConnection) oracleDataSource.getOracleDataSource().getConnection()) {
            String querySelectInquiries = "SELECT ri.*, rf.followup_id as flpid FROM raagatech_inquiry ri "
                    + " LEFT JOIN RAAGATECH_FOLLOWUPDETAILS rf ON ri.INQUIRY_ID = rf.INQUIRY_ID "
                    + " WHERE ri.exam_session = '" + examSession + "' ";
            /*if (inspiratorId >= 0 && userId == 2) {
                querySelectInquiries = querySelectInquiries + " AND ri.inspirator_id = " + inspiratorId;
            } else*/ if (inspiratorId > 0) {
                querySelectInquiries = querySelectInquiries + " AND ri.inspirator_id = " + inspiratorId;
            } else if (userId != 2) {
                querySelectInquiries = querySelectInquiries + " AND ri.user_id = " + userId;
            }
            if (inquiryStatusId >= 1) {
                querySelectInquiries = querySelectInquiries + " AND rf.INQUIRYSTATUS_ID = " + inquiryStatusId;
            }
            querySelectInquiries = querySelectInquiries + " order by ri.INQUIRY_ID ";
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
                inquiry.setExamSession(record.getString("exam_session"));
                inquiry.setPrimaryskill(record.getString("primaryskill"));
                inquiry.setEmailVerified(record.getInt("emailverification"));
                inquiry.setMobileVerified(record.getInt("mobileverification"));
                inquiry.setExamFees(record.getInt("exam_fees"));
                inquiry.setFeesPaidStatus(record.getInt("fees_paid_status"));

                if (record.getString("flpid") == null) {
                    inquiry.setFollowup_details("0");
                } else {
                    inquiry.setFollowup_details(record.getString("flpid"));
                }

                inquiryList.add(inquiry);
            }
        }
        return inquiryList;
    }

    @Override
    public boolean updateInquiry(int inquiry_id, String inquiryname, int inspirationid, String email, long mobileNo,
            int levelid, String address, String followupDetails, String nationality,
            String dob, long telOther, String image, String gender, int inspirator_id,
            String comfortability, String primaryskill, int userId, int pinCode,
            String examSession, String fatherName, String motherName, int examFees, int inquiryStatusId) throws Exception {
        boolean updateStatus = Boolean.FALSE;
        // With AutoCloseable, the connection is closed automatically.
        try ( OracleConnection connection = (OracleConnection) oracleDataSource.getOracleDataSource().getConnection()) {
            char sex = gender.equals("Male") ? 'M' : 'F';
            String queryUpdateInquiry = "UPDATE raagatech_inquiry set firstname = '" + inquiryname + "', inspiration_id = " + inspirationid
                    + ", email = '" + email + "', mobile = " + mobileNo + ", level_id = " + levelid + ", address_line1 = '" + address + "', gender = '" + sex + "',"
                    + " pincode = " + pinCode + ", exam_session = '" + examSession + "', primaryskill = '" + primaryskill + "'" + ", date_of_birth = to_date(?, 'dd-mm-yyyy')"
                    + ", father_name = '" + fatherName + "' " + ", mother_name = '" + motherName + "', exam_fees = " + examFees + ", inspirator_id = " + inspirator_id
                    + " WHERE inquiry_id = " + inquiry_id;

            PreparedStatement statement = connection.prepareStatement(queryUpdateInquiry);
            statement.setString(1, dob);
            int records = statement.executeUpdate();
            if (records > 0) {
                String queryUpdateFollowupDetails = "UPDATE raagatech_followupdetails set followup_details = '" + followupDetails + "', inquirystatus_id = " + inquiryStatusId
                        + " WHERE inquiry_id = " + inquiry_id;
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
    public InquiryBean getInquiryById(int inquiryId, String followUpId) throws Exception {
        InquiryBean inquiry = null;
        // With AutoCloseable, the connection is closed automatically.
        try ( OracleConnection connection = (OracleConnection) oracleDataSource.getOracleDataSource().getConnection()) {
            String queryInsertUser = "SELECT ri.*, rf.followup_details, rf.inquirystatus_id "
                    + "FROM raagatech_inquiry ri join raagatech_followupdetails rf on ri.inquiry_id = rf.inquiry_id "
                    + "WHERE ri.inquiry_id = " + inquiryId;
            if (followUpId != null) {
                queryInsertUser = queryInsertUser + "AND rf.followup_id = " + followUpId;
            }
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
                inquiry.setExamSession(record.getString("exam_session"));
                inquiry.setAddress_line1(record.getString("address_line1"));
                inquiry.setFollowup_details(record.getString("followup_details"));
                inquiry.setPrimaryskill(record.getString("primaryskill"));
                if (record.getDate("date_of_birth") != null) {
                    inquiry.setDate_of_birth(dateFormat.format(record.getDate("date_of_birth")));
                }
                inquiry.setFather_name(record.getString("father_name"));
                inquiry.setMother_name(record.getString("mother_name"));
                inquiry.setEmailVerified(record.getInt("emailverification"));
                inquiry.setMobileVerified(record.getInt("mobileverification"));
                inquiry.setExamFees(record.getInt("exam_fees"));
                inquiry.setFeesPaidStatus(record.getInt("fees_paid_status"));
                inquiry.setInquirystatus_id(record.getInt("inquirystatus_id"));
                inquiry.setInspiratorId(record.getInt("inspirator_id"));
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
        UserDataBean userData = new UserDataBean();
        // With AutoCloseable, the connection is closed automatically.
        try ( OracleConnection connection = (OracleConnection) oracleDataSource.getOracleDataSource().getConnection()) {
            String queryInsertUser = "SELECT ru.*, rim.INSPIRATOR_ID as rim_id FROM RAAGATECH_USER ru left join RAAGATECH_INSPIRATORMASTER rim "
                    + "ON ru.email = rim.email AND ru.mobile = rim.MOBILE "
                    + "WHERE (ru.email = '" + username + "' OR ru.username = '" + username + "') "
                    + "AND ru.password = '" + password + "' AND ru.emailverification = 1";

            PreparedStatement statement = connection.prepareStatement(queryInsertUser);
            ResultSet record = statement.executeQuery();
            while (record.next()) {
                userData.setUserName(record.getString("username"));
                userData.setPassword(record.getString("password"));
                userData.setEmail(record.getString("email"));
                userData.setCreationDate(record.getDate("creation_date"));
                userData.setCountryCode(record.getInt("country_code"));
                userData.setMobile(record.getLong("mobile"));
                userData.setUserId(record.getInt("user_id"));
                userData.setPincode(record.getInt("pincode"));
                if (record.getString("rim_id") == null) {
                    userData.setInspiratorId(0);
                } else {
                    userData.setInspiratorId(record.getInt("rim_id"));
                }
                userData.setGender(record.getString("gender").equals("M") ? 'M' : 'F');
                userData.setAddress(record.getString("postaladdress"));
                userData.setMobileVerified(record.getInt("mobileverification"));
            }

            List<String> inspiratorList = new ArrayList<>();
            String querySelectEducators = "SELECT * from RAAGATECH_INSPIRATORMASTER order by inspirator_id ";

            statement = connection.prepareStatement(querySelectEducators);
            record = statement.executeQuery();
            while (record.next()) {
                String inspiratorData = record.getInt("inspirator_id") + "/" + record.getString("first_name") + "/" + record.getInt("pss_discount");
                inspiratorList.add(inspiratorData);
            }
            userData.setInspiratorList(inspiratorList);

            List<String> inspirationList = new ArrayList<>();
            inspirationList.add("");
            String querySelectInspiration = "SELECT * FROM RAAGATECH_INSPIRATIONMASTER order by inspiration_id";

            statement = connection.prepareStatement(querySelectInspiration);
            record = statement.executeQuery();
            while (record.next()) {
                inspirationList.add(record.getString("inspirationname"));
            }
            userData.setInspirationList(inspirationList);

            List<String> examFeesList = new ArrayList<>();
            examFeesList.add("0/NaN/0/0");
            String querySelectExamFees = "select * from RAAGATECH_LEVELMASTER order by level_id";

            statement = connection.prepareStatement(querySelectExamFees);
            record = statement.executeQuery();
            while (record.next()) {
                String examFeesData = record.getInt("level_id") + "/" + record.getString("levelname") + "/" + record.getInt("exam_fees") + "/" + record.getInt("pss_exam_fees");
                examFeesList.add(examFeesData);
            }
            userData.setExamFeesList(examFeesList);
            userData.setInspirationList(inspirationList);
        }

        List<String> inquiryStatusList = Arrays.asList("", "Inquiry", "Leads", "Follow-up", "Aspirant", "Registration", "Admission", "Feedback", "PSS Exam", "Certified", "Meeting", "Contacts");
        userData.setInquiryStatusList(inquiryStatusList);
        return userData;
    }

    @Override
    public boolean updateUserData(String username, String password, long mobileNo,
            String gender, String postalAddress, String pincode, int userId, int inspiratorId, int discount) throws Exception {
        boolean updateStatus = Boolean.FALSE;
        // With AutoCloseable, the connection is closed automatically.
        try ( OracleConnection connection = (OracleConnection) oracleDataSource.getOracleDataSource().getConnection()) {
            char sex = gender.equals("Male") ? 'M' : 'F';
            String queryUpdateUser = "UPDATE raagatech_user set username = '" + username + "', password = '" + password + "'"
                    + ", mobile = " + mobileNo + ", gender = '" + sex + "', pincode = " + pincode + ""
                    + ", inspirator_id = " + inspiratorId + ", postaladdress = '" + postalAddress + "'"
                    + " WHERE user_id = " + userId;
            PreparedStatement statement = connection.prepareStatement(queryUpdateUser);
            int records = statement.executeUpdate();
            if (records > 0) {
                updateStatus = Boolean.TRUE;
            }
            if (updateStatus && discount > 0 && inspiratorId > 0) {
                queryUpdateUser = "UPDATE raagatech_inspiratormaster set pss_discount = " + discount
                        + " WHERE inspirator_id = " + inspiratorId;
                statement = connection.prepareStatement(queryUpdateUser);
                statement.executeUpdate();
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
    public ArrayList<UserDataBean> listOverAllContacts(String dob) throws Exception {

        ArrayList<UserDataBean> contactsList = new ArrayList<>();
        UserDataBean userData;
        // With AutoCloseable, the connection is closed automatically.
        try ( OracleConnection connection = (OracleConnection) oracleDataSource.getOracleDataSource().getConnection()) {
            String querySelectUser = "select DISTINCT ru.USERNAME as name, ru.EMAIL as email from RAAGATECH_USER ru"
                    + " UNION"
                    + " select DISTINCT ri.FIRSTNAME as name, ri.EMAIL as email from RAAGATECH_INQUIRY ri";
            if (dob != null) {
                querySelectUser = querySelectUser.concat(" WHERE date_of_birth = '" + dob + "'");
            }
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
                userData.setMobileVerified(record.getInt("mobileverification"));
            }
        }
        return userData;
    }

    @Override
    public boolean updateUserForMobileVerification(int userId, String email, long mobileNo) throws Exception {
        boolean updateStatus = Boolean.FALSE;
        // With AutoCloseable, the connection is closed automatically.
        try ( OracleConnection connection = (OracleConnection) oracleDataSource.getOracleDataSource().getConnection()) {
            String queryUpdateUser = "update raagatech_user set mobileverification = 1 where user_id = " + userId + " AND email = '" + email + "' AND mobile = " + mobileNo;
            PreparedStatement statement = connection.prepareStatement(queryUpdateUser);
            int records = statement.executeUpdate();
            if (records > 0) {
                updateStatus = Boolean.TRUE;
            }
        }
        return updateStatus;
    }

    @Override
    public boolean updateInquiryForEmailVerification(int inquiryId, String email) throws Exception {
        boolean updateStatus = Boolean.FALSE;
        // With AutoCloseable, the connection is closed automatically.
        try ( OracleConnection connection = (OracleConnection) oracleDataSource.getOracleDataSource().getConnection()) {
            String queryUpdateUser = "update raagatech_inquiry set emailverification = 1 where inquiry_id = " + inquiryId + " AND email = '" + email + "'";
            PreparedStatement statement = connection.prepareStatement(queryUpdateUser);
            int records = statement.executeUpdate();
            if (records > 0) {
                updateStatus = Boolean.TRUE;
            }
        }
        return updateStatus;
    }

    @Override
    public boolean updateInquiryForMobileVerification(int inquiryId, long mobileNo) throws Exception {
        boolean updateStatus = Boolean.FALSE;
        // With AutoCloseable, the connection is closed automatically.
        try ( OracleConnection connection = (OracleConnection) oracleDataSource.getOracleDataSource().getConnection()) {
            String queryUpdateUser = "update raagatech_inquiry set mobileverification = 1 where inquiry_id = " + inquiryId + " AND mobile = " + mobileNo;
            PreparedStatement statement = connection.prepareStatement(queryUpdateUser);
            int records = statement.executeUpdate();
            if (records > 0) {
                updateStatus = Boolean.TRUE;
            }
        }
        return updateStatus;
    }

    @Override
    public boolean updateInquiryForFeesPaidStatus(int inquiryId, int amount) throws Exception {
        boolean updateStatus = Boolean.FALSE;
        // With AutoCloseable, the connection is closed automatically.
        try ( OracleConnection connection = (OracleConnection) oracleDataSource.getOracleDataSource().getConnection()) {
            String queryUpdateUser = "update raagatech_inquiry set fees_paid_status = 1 where inquiry_id = " + inquiryId + " AND EXAM_FEES = " + amount;
            PreparedStatement statement = connection.prepareStatement(queryUpdateUser);
            int records = statement.executeUpdate();
            if (records > 0) {
                updateStatus = Boolean.TRUE;
            }
        }
        return updateStatus;
    }

    @Override
    public boolean addFollowUps(String name, String email, long mobile, String followupDetails, int inquiryStatusId) throws Exception {
        boolean insertStatus;

        // With AutoCloseable, the connection is closed automatically.
        try ( OracleConnection connection = (OracleConnection) oracleDataSource.getOracleDataSource().getConnection()) {

            int followup_id = oracleDataSource.generateNextPrimaryKey("raagatech_followupdetails", "followup_id");
            String queryInsertFollowupDetails = "INSERT into raagatech_followupdetails (followup_id, name, mobile, followup_details, followup_date, inquiry_id, inquirystatus_id) "
                    + "VALUES (" + followup_id + ", '" + name + "', " + mobile + ", '" + followupDetails + "',?, 1, " + inquiryStatusId + ")";
            PreparedStatement statement2 = connection.prepareStatement(queryInsertFollowupDetails);
            statement2.setTimestamp(1, getCurrentTimeStamp());
            insertStatus = statement2.execute();
        }
        return insertStatus;
    }

    @Override
    public List<Feedback> getFollowUps(int inquiryStatusId) throws Exception {
        List<Feedback> feedbackList = new ArrayList();
        // With AutoCloseable, the connection is closed automatically.
        try ( OracleConnection connection = (OracleConnection) oracleDataSource.getOracleDataSource().getConnection()) {
            String queryInsertUser = "SELECT * FROM raagatech_followupdetails WHERE inquirystatus_id = " + inquiryStatusId;
            PreparedStatement statement = connection.prepareStatement(queryInsertUser);
            ResultSet record = statement.executeQuery();
            while (record.next()) {
                Feedback feedback = new Feedback();
                feedback.setName(record.getString("name"));
                feedback.setEmail(record.getString("email"));
                feedback.setMobile(record.getLong("mobile"));
                feedback.setFollowupDetails(record.getString("followup_details"));
                feedback.setFollowup_date(record.getDate("followup_date"));
                feedbackList.add(feedback);
            }
        }
        return feedbackList;
    }

    @Override
    public ArrayList<PssExamReportBean> generatePssExamReport(int userId, int inspiratorId, String examSession, int inquiryStatusId, int reportType) throws Exception {

        ArrayList<PssExamReportBean> subjectWiseReportList = new ArrayList<>();
        // With AutoCloseable, the connection is closed automatically.
        try ( OracleConnection connection = (OracleConnection) oracleDataSource.getOracleDataSource().getConnection()) {
            String queryPssExamReport = "SELECT ri.level_id as yearId, count(ri.inspiration_id) as totalforms, sum(ri.EXAM_FEES) as totalfees, "
                    + " sum(ri.FEES_PAID_STATUS) as feescollectedcount, sum(rl.pss_exam_fees) as totalpssfees , sum(rim.pss_discount) as totaldiscount ";
            if (reportType == 1) {
                queryPssExamReport = queryPssExamReport + " , ri.inspiration_id as subjectId  ";
            }
            if (reportType == 3) {
                queryPssExamReport = queryPssExamReport + " , ri.inspirator_id as educatorId , ri.inspiration_id as subjectId ";
            }
            if (reportType == 4 || reportType == 5) {
                queryPssExamReport = "SELECT count(ri.inquiry_id) as totalforms, sum(ri.EXAM_FEES) as totalfees, "
                        + " sum(ri.FEES_PAID_STATUS) as feescollectedcount , sum(rl.pss_exam_fees) as totalpssfees, sum(rim.pss_discount) as totaldiscount ";
            }
            if (reportType == 5) {
                queryPssExamReport = queryPssExamReport + " , rim.inspirator_id as educatorId, rim.pss_discount";
            }
            queryPssExamReport = queryPssExamReport + " FROM raagatech_inquiry ri "
                    + " join RAAGATECH_INSPIRATORMASTER rim on ri.inspirator_id = rim.inspirator_id "
                    + " join RAAGATECH_LEVELMASTER rl on ri.LEVEL_ID = rl.LEVEL_ID "
                    + " LEFT JOIN RAAGATECH_FOLLOWUPDETAILS rf ON ri.INQUIRY_ID = rf.INQUIRY_ID "
                    + " WHERE ri.exam_session = '" + examSession + "' AND ri.inspirator_id > 0 AND rf.INQUIRYSTATUS_ID = 1 AND ri.user_id = " + userId;

            switch (reportType) {
                case 1:
                    queryPssExamReport = queryPssExamReport + " group by ri.inspiration_id, ri.level_id order by ri.inspiration_id, ri.level_id";
                    break;
                case 3:
                    queryPssExamReport = queryPssExamReport + " group by ri.inspirator_id, ri.inspiration_id, ri.level_id order by ri.inspirator_id, ri.inspiration_id, ri.level_id";
                    break;
                case 2:
                    queryPssExamReport = queryPssExamReport + " group by ri.level_id order by ri.level_id";
                    break;
                case 5:
                    queryPssExamReport = queryPssExamReport + " group by rim.inspirator_id, rim.pss_discount order by rim.inspirator_id";
                    break;
                default:
                    break;
            }
            PreparedStatement statement = connection.prepareStatement(queryPssExamReport);
            ResultSet record = statement.executeQuery();
            while (record.next()) {
                PssExamReportBean reportBean = new PssExamReportBean();
                if (reportType == 3 || reportType == 5) {
                    reportBean.setEducatorId(record.getInt("educatorId"));
                } else {
                    reportBean.setEducatorId(0);
                }
                if (reportType == 1 || reportType == 3) {
                    reportBean.setSubjectId(record.getInt("subjectId"));
                } else {
                    reportBean.setSubjectId(0);
                }
                if (reportType < 4) {
                    reportBean.setYearId(record.getInt("yearId"));
                } else {
                    reportBean.setYearId(0);
                }
                reportBean.setTotalForms(record.getInt("totalforms"));
                reportBean.setTotalFees(record.getInt("totalfees"));
                reportBean.setTotalPssFees(record.getInt("totalpssfees"));
                reportBean.setTotalFeesCollectedCount(record.getInt("feescollectedcount"));
                reportBean.setTotalDiscount(record.getInt("totaldiscount"));
                subjectWiseReportList.add(reportBean);
            }
        }
        return subjectWiseReportList;
    }

    @Override
    public ArrayList<UserDataBean> listEducators(int userId, String examSession) throws Exception {
        ArrayList<UserDataBean> usersList = new ArrayList<>();
        UserDataBean userData;
        // With AutoCloseable, the connection is closed automatically.
        try ( OracleConnection connection = (OracleConnection) oracleDataSource.getOracleDataSource().getConnection()) {
            String querySelectEducators = "SELECT ru.user_id as user_id, rim.* FROM RAAGATECH_USER ru right join RAAGATECH_INSPIRATORMASTER rim "
                    + " ON ru.email = rim.email AND ru.mobile = rim.MOBILE order by rim.inspirator_id";

            PreparedStatement statement = connection.prepareStatement(querySelectEducators);
            ResultSet record = statement.executeQuery();
            while (record.next()) {
                userData = new UserDataBean();
                userData.setUserName(record.getString("first_name"));
                userData.setAddress(record.getString("address_line1"));
                userData.setEmail(record.getString("email"));
                userData.setMobile(record.getLong("mobile"));
                userData.setInspiratorId(record.getInt("inspirator_id"));
                userData.setSpecialization(record.getString("specialization"));
                if (record.getString("user_id") != null) {
                    userData.setUserId(record.getInt("user_id"));
                }
                userData.setDiscount(record.getInt("pss_discount"));
                usersList.add(userData);
            }
        }
        return usersList;
    }

    @Override
    public boolean createEducator(String username, String password, String email, long mobileNo,
            String gender, String postalAddress, String pincode, int userId, int discount) throws Exception {

        boolean isInserted;
        // With AutoCloseable, the connection is closed automatically.
        int id = oracleDataSource.generateNextPrimaryKey("raagatech_inspiratormaster", "inspirator_id");
        try ( OracleConnection connection = (OracleConnection) oracleDataSource.getOracleDataSource().getConnection()) {
            char sex = gender.equals("Male") ? 'M' : 'F';
            String queryInsertUser = "INSERT into raagatech_inspiratormaster (inspirator_id, first_name, date_of_birth, email, country_code, mobile, "
                    + "address_line1, address_line2, specialization, pss_discount, date_of_joining) "
                    + "VALUES (" + id + ", '" + username + "',?, '" + email + "', 091, " + mobileNo
                    + ",  '" + postalAddress + "', " + pincode + ", 1, " + discount + ", ?)";
            PreparedStatement statement = connection.prepareStatement(queryInsertUser);
            statement.setTimestamp(1, getCurrentTimeStamp());
            statement.setTimestamp(2, getCurrentTimeStamp());
            isInserted = statement.execute();
            if (isInserted) {
                String queryUpdateUser = "UPDATE raagatech_user set inspirator_id = " + id
                        + " WHERE email = '" + email + "' AND mobileNo = " + mobileNo;
                statement = connection.prepareStatement(queryUpdateUser);
                statement.executeUpdate();
            }
        }
        return isInserted;
    }

    @Override
    public UserDataBean getEducatorData(int userId, int inspiratorId) throws Exception {
        UserDataBean userData = null;
        // With AutoCloseable, the connection is closed automatically.
        try ( OracleConnection connection = (OracleConnection) oracleDataSource.getOracleDataSource().getConnection()) {
            String querySelectEducator = "SELECT * FROM raagatech_inspiratormaster WHERE inspirator_id = " + inspiratorId;
            PreparedStatement statement = connection.prepareStatement(querySelectEducator);
            ResultSet record = statement.executeQuery();
            while (record.next()) {
                userData = new UserDataBean();
                userData.setUserName(record.getString("first_name"));
                userData.setSpecialization(record.getString("specialization"));
                userData.setEmail(record.getString("email"));
                userData.setMobile(record.getLong("mobile"));
                userData.setPincode(record.getString("address_line2") == null ? 0 : Integer.parseInt(record.getString("address_line2")));
                userData.setInspiratorId(record.getInt("inspirator_id"));
//                userData.setGender(record.getString("gender").equals("M") ? 'M' : 'F');
                userData.setAddress(record.getString("address_line1"));
                userData.setDiscount(record.getInt("pss_discount"));
            }
        }
        return userData;
    }

    @Override
    public boolean updateEducator(String username, String specialisation, String email, long mobileNo,
            String gender, String postalAddress, String pincode, int userId, int inspiratorId, int discount) throws Exception {

        int record;
        // With AutoCloseable, the connection is closed automatically.
        try ( OracleConnection connection = (OracleConnection) oracleDataSource.getOracleDataSource().getConnection()) {
            char sex = gender.equals("Male") ? 'M' : 'F';
            String queryUpdateEducator = "UPDATE raagatech_inspiratormaster set "
                    + "first_name = '" + username + "', email = '" + email + "', mobile = " + mobileNo
                    + ", address_line1 = '" + postalAddress + "', address_line2 = '" + pincode + "', pss_discount = " + discount + " WHERE inspirator_id = " + inspiratorId;
            PreparedStatement statement = connection.prepareStatement(queryUpdateEducator);
            record = statement.executeUpdate();
        }
        return record == 1;
    }
}
