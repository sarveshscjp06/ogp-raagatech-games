/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.raagatech.omp.musicapp;

import com.raagatech.common.datasource.OracleDatabaseInterface;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
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
    public boolean insertUser(String username, String password, String email, long mobileNo) throws Exception {

        boolean insertStatus = Boolean.FALSE;
        // With AutoCloseable, the connection is closed automatically.
        int id = oracleDataSource.generateNextPrimaryKey("raagatech_user", "user_id");
        try ( OracleConnection connection = (OracleConnection) oracleDataSource.getOracleDataSource().getConnection()) {
            String queryInsertUser = "INSERT into raagatech_user (user_id, username, password, creation_date, email, country_code, mobile) "
                    + "VALUES ("+id+", '" + username + "','" + password + "',?, '" + email + "', 091, " + mobileNo + ")";
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
    public boolean insertInquiry(String inquiryname, int inspirationid, String email, long mobileNo, int levelid, String address, String followupDetails, String nationality, String fname, String mname, String dob, long telOther, String image, String gender, String inspiration, String comfortability, String primaryskill) throws Exception {
        boolean insertStatus = Boolean.FALSE;
        // With AutoCloseable, the connection is closed automatically.
        try ( OracleConnection connection = (OracleConnection) oracleDataSource.getOracleDataSource().getConnection()) {
            Statement statement = connection.createStatement();
            String queryInsertInquiry = "INSERT into raagatech_inquiry (firstname, inspiration_id, inquiry_date, email, mobile"
                    + ", level_id, address_line1, nationality, father_name, mother_name, date_of_birth, telephone, photo, gender, inspiration, comfortability, primaryskill) "
                    + "VALUES ('" + inquiryname + "'," + inspirationid + ",'" + FORMATTER.format(new Date()) + "', '" + email + "', " + mobileNo + ","
                    + levelid + ", '" + address + "', '" + nationality + "', '" + fname + "', '" + mname + "', '" + FORMATTER.format(new SimpleDateFormat("dd/MM/yyyy").parse(dob)) + "', " + telOther + ", '" + image + "', '"
                    + gender + "', '" + inspiration + "', '" + comfortability + "', '" + primaryskill + "')";
            int records = statement.executeUpdate(queryInsertInquiry);
            if (records > 0) {
                statement = connection.createStatement();
                ResultSet rs = statement.executeQuery("SELECT MAX(inquiry_id) from inquiry");
                int inquiry_id = 0;
                while (rs.next()) {
                    inquiry_id = rs.getInt(1);
                }
                rs.close();
                statement = connection.createStatement();
                String queryInsertFollowupDetails = "INSERT into raagatech_followupdetails (inquiry_id, inquirystatus_id, followup_details, followup_date) "
                        + "VALUES (" + inquiry_id + ", 1, '" + followupDetails + "','" + FORMATTER.format(new Date()) + "')";
                records = statement.executeUpdate(queryInsertFollowupDetails);
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
    public ArrayList<InquiryBean> listInquiry() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
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
}
