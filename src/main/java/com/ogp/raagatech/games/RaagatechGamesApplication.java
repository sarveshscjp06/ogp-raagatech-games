package com.ogp.raagatech.games;

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
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class RaagatechGamesApplication {

    final static String DB_URL = "jdbc:oracle:thin:@(description= (retry_count=20)(retry_delay=3)(address=(protocol=tcps)(port=1521)(host=adb.ap-mumbai-1.oraclecloud.com))(connect_data=(service_name=ge9bf8133738252_raagatechdb_high.adb.oraclecloud.com))(security=(ssl_server_dn_match=yes)))";
    final static String DB_USER = "ADMIN";
    final static String DB_PASSWORD = "Sarvesh12345";
    protected char delimiter = ' ';

    public static void main(String[] args) {
        SpringApplication.run(RaagatechGamesApplication.class, args);
    }

    @RequestMapping
    public String home() throws SQLException {

        String test_table_users = "";

        try {
            test_table_users = getOracleDataSource();
        } catch (SQLException ex) {
            Logger.getLogger(RaagatechGamesApplication.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "<h1>Spring Boot Hello World!</h1><br/>" + test_table_users;
    }

    public String getOracleDataSource() throws SQLException {

        StringBuilder out = new StringBuilder();
        Properties info = new Properties();
        info.put(OracleConnection.CONNECTION_PROPERTY_USER_NAME, DB_USER);
        info.put(OracleConnection.CONNECTION_PROPERTY_PASSWORD, DB_PASSWORD);
        info.put(OracleConnection.CONNECTION_PROPERTY_DEFAULT_ROW_PREFETCH, "20");

        var ods = new OracleDataSource();
        ods.setURL(DB_URL);
        ods.setConnectionProperties(info);

        // With AutoCloseable, the connection is closed automatically.
        try ( OracleConnection connection = (OracleConnection) ods.getConnection()) {
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
}
