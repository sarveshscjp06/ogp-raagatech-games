/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.admin.raagatech.datasource;

import com.omp.raagatech.musicapp.InquiryBean;
import com.omp.raagatech.musicapp.SliderImageBean;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import oracle.jdbc.pool.OracleDataSource;

/**
 *
 * @author sarve
 */
public interface OracleDatabaseInterface {

    public OracleDataSource getOracleDataSource() throws Exception;

    public boolean insertUser(String username, String password, String email, long mobileNo) throws Exception;

    public boolean insertInquiry(String inquiryname, int inspirationid, String email, long mobileNo,
            int levelid, String address, String followupDetails,
            String nationality, String fname, String mname, String dob, long telOther, String image,
            String gender, String inspiration, String comfortability, String primaryskill) throws Exception;

    public LinkedHashMap<Integer, String> selectLevel() throws Exception;

    public LinkedHashMap<Integer, String> selectInspiration() throws Exception;

    public ArrayList<InquiryBean> listInquiry() throws Exception;

    public boolean updateInquiry(int inquiry_id, String inquiryname, int inspirationid, String email, long mobileNo,
            int levelid, String address, String followupDetails, String nationality, String fname, String mname, String dob, long telOther, String image,
            String gender, String inspiration, String comfortability, String primaryskill) throws Exception;

    public LinkedHashMap<Integer, String> selectInquiryStatus() throws Exception;

    public boolean updateFollowup(int inquiry_id, int inquirystatus_id, String followupDetails) throws Exception;

    public InquiryBean getInquiryById(int inquiryId) throws Exception;

    public int generateNextPrimaryKey(String tableName, String columnName) throws Exception;

    public ArrayList<SliderImageBean> listSliderImage();

    public int selectInquiry(String email, long mobileNo) throws Exception;

    public InquiryBean getInquiryDetails(String email, long mobileNo) throws Exception;

}
