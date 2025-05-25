/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.raagatech.common.datasource;

import com.raagatech.ogp.gamesapp.RaagatechGamesApplication;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import oracle.jdbc.OracleConnection;
import oracle.jdbc.pool.OracleDataSource;
import oracle.ucp.jdbc.PoolDataSource;
import oracle.ucp.jdbc.PoolDataSourceFactory;
import org.springframework.stereotype.Service;

/**
 *
 * @author sarve
 */
@Service
public class DatabaseSource implements OracleDatabaseInterface {

    final static String DB_URL = "jdbc:oracle:thin:@(description= (retry_count=20)(retry_delay=3)(address=(protocol=tcps)(port=1521)(host=adb.ap-mumbai-1.oraclecloud.com))(connect_data=(service_name=ge9bf8133738252_raagatechdb_high.adb.oraclecloud.com))(security=(ssl_server_dn_match=yes)))";
    final static String DB_USER = "ADMIN";
    final static String DB_PASSWORD = "Rudransh12345";
    protected char delimiter = ' ';
    final static String CONN_FACTORY_CLASS_NAME = "oracle.jdbc.pool.OracleDataSource";

    @Override
    public OracleDataSource getOracleDataSource() throws SQLException {

        Properties info = new Properties();
        info.put(OracleConnection.CONNECTION_PROPERTY_USER_NAME, DB_USER);
        info.put(OracleConnection.CONNECTION_PROPERTY_PASSWORD, DB_PASSWORD);
        info.put(OracleConnection.CONNECTION_PROPERTY_DEFAULT_ROW_PREFETCH, "20");

        var ods = new OracleDataSource();
        ods.setURL(DB_URL);
        ods.setConnectionProperties(info);
        return ods;
    }

    @Override
    public int generateNextPrimaryKey(String tableName, String columnName) throws Exception {

        int nextInt = 0;
        try ( OracleConnection connection = (OracleConnection) getOracleDataSource().getConnection()) {
            Statement statement = connection.createStatement();
            String selectQuery = "select COALESCE(max(" + columnName + "), 0) + 1 as primary_key from " + tableName;
            ResultSet rs = statement.executeQuery(selectQuery);
            while (rs.next()) {
                nextInt = rs.getInt("primary_key");
            }
        }
        return nextInt;
    }

    @Override
    public String databaseConnectionTest() throws SQLException, Exception {

        String test_table_users = "";

        try {
            test_table_users = getOracleDatabaseSource();
            //other way to get db connection
//            getUCPoolDataSource();
        } catch (SQLException ex) {
            Logger.getLogger(RaagatechGamesApplication.class.getName()).log(Level.SEVERE, null, ex);
        }
        return test_table_users;
    }

    public String getOracleDatabaseSource() throws SQLException {

        StringBuilder out = new StringBuilder();

        // With AutoCloseable, the connection is closed automatically.
        try ( OracleConnection connection = (OracleConnection) getOracleDataSource().getConnection()) {
            // Get the JDBC driver name and version 
            DatabaseMetaData dbmd = connection.getMetaData();
            String driverName = "\nDriver Name: " + dbmd.getDriverName() + this.delimiter;
            out.append(driverName);
            String driverVersion = "\nDriver Version: " + dbmd.getDriverVersion() + this.delimiter;
            out.append(driverVersion);
            // Print some connection properties
            String defaultRowPrefetchValue = "\nDefault Row Prefetch Value is: " + connection.getDefaultRowPrefetch();
            out.append(defaultRowPrefetchValue);
            String dbUserName = connection.getUserName();
            dbUserName = "\nDatabase Username is: " + dbUserName;
            out.append(dbUserName);
            // Perform a database operation 
            String employeeDetail = printEmployees(connection);
            out.append("<br/>");
            out.append(employeeDetail);
        }
        return out.toString();
    }

    /*
    * Displays first_name and last_name from the test table.
     */
    public String printEmployees(Connection connection) throws SQLException {
        String employeeDetail;
        // Statement and ResultSet are AutoCloseable and closed automatically. 
        try ( Statement statement = connection.createStatement()) {
            try ( ResultSet resultSet = statement
                    .executeQuery("select id, name from test_table")) {
                employeeDetail = "<br/>FIRST_NAME" + "  " + "LAST_NAME";
                employeeDetail += "<br/>---------------------";
                while (resultSet.next()) {
                    employeeDetail += "<br/>" + resultSet.getInt(1) + " "
                            + resultSet.getString(2) + " ";
                }
            }
        }
        return employeeDetail;
    }

    public PoolDataSource getUCPoolDataSource() throws Exception {
        // Get the PoolDataSource for UCP
        PoolDataSource pds = PoolDataSourceFactory.getPoolDataSource();
        // Set the connection factory first before all other properties
        pds.setConnectionFactoryClassName(CONN_FACTORY_CLASS_NAME);
        pds.setURL(DB_URL);
        pds.setUser(DB_USER);
        pds.setPassword(DB_PASSWORD);
        pds.setConnectionPoolName("JDBC_UCP_POOL");

        // Default is 0. Set the initial number of connections to be created
        // when UCP is started.
        pds.setInitialPoolSize(5);

        // Default is 0. Set the minimum number of connections
        // that is maintained by UCP at runtime.
        pds.setMinPoolSize(5);

        // Default is Integer.MAX_VALUE (2147483647). Set the maximum number of
        // connections allowed on the connection pool.
        pds.setMaxPoolSize(20);

        // Default is 30secs. Set the frequency in seconds to enforce the timeout
        // properties. Applies to inactiveConnectionTimeout(int secs),
        // AbandonedConnectionTimeout(secs)& TimeToLiveConnectionTimeout(int secs).
        // Range of valid values is 0 to Integer.MAX_VALUE. .
        pds.setTimeoutCheckInterval(5);

        // Default is 0. Set the maximum time, in seconds, that a
        // connection remains available in the connection pool.
        pds.setInactiveConnectionTimeout(10);

        // Get the database connection from UCP.
        try ( Connection conn = pds.getConnection()) {
            System.out.println("Available connections after checkout: "
                    + pds.getAvailableConnectionsCount());
            System.out.println("Borrowed connections after checkout: "
                    + pds.getBorrowedConnectionsCount());
            // Perform a database operation
            doSQLWork(conn);
        } catch (SQLException e) {
            System.out.println("UCPSample - " + "SQLException occurred : "
                    + e.getMessage());
        }
        System.out.println("Available connections after checkin: "
                + pds.getAvailableConnectionsCount());
        System.out.println("Borrowed connections after checkin: "
                + pds.getBorrowedConnectionsCount());

        return pds;
    }

    /*
    * Creates an EMP table and does an insert, update and select operations on
    * the new table created.
     */
    public void doSQLWork(Connection conn) {
        try {
            conn.setAutoCommit(false);
            // Prepare a statement to execute the SQL Queries.
            Statement statement = conn.createStatement();
            // Create table EMP
            statement.executeUpdate("create table EMP(EMPLOYEEID NUMBER,"
                    + "EMPLOYEENAME VARCHAR2 (20))");
            System.out.println("New table EMP is created");
            // Insert some records into the table EMP
            statement.executeUpdate("insert into EMP values(1, 'Jennifer Jones')");
            statement.executeUpdate("insert into EMP values(2, 'Alex Debouir')");
            System.out.println("Two records are inserted.");

            // Update a record on EMP table.
            statement.executeUpdate("update EMP set EMPLOYEENAME='Alex Deborie'"
                    + " where EMPLOYEEID=2");
            System.out.println("One record is updated.");

            // Verify the table EMP
            ResultSet resultSet = statement.executeQuery("select * from EMP");
            System.out.println("\nNew table EMP contains:");
            System.out.println("EMPLOYEEID" + " " + "EMPLOYEENAME");
            System.out.println("--------------------------");
            while (resultSet.next()) {
                System.out.println(resultSet.getInt(1) + " " + resultSet.getString(2));
            }
            System.out.println("\nSuccessfully tested a connection from UCP");
        } catch (SQLException e) {
            System.out.println("UCPSample - "
                    + "doSQLWork()- SQLException occurred : " + e.getMessage());
        } finally {
            // Clean-up after everything
            try ( Statement statement = conn.createStatement()) {
                statement.execute("drop table EMP");
            } catch (SQLException e) {
                System.out.println("UCPSample - "
                        + "doSQLWork()- SQLException occurred : " + e.getMessage());
            }
        }
    }
}
