/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.raagatech.common.datasource;

import java.sql.SQLException;
import oracle.jdbc.pool.OracleDataSource;

/**
 *
 * @author sarve
 */
public interface OracleDatabaseInterface {

    public OracleDataSource getOracleDataSource() throws Exception;
    
    public int generateNextPrimaryKey(String tableName, String columnName) throws Exception;
    
    public String databaseConnectionTest() throws SQLException, Exception;

}
