/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.raagatech;

import com.raagatech.common.datasource.OracleDatabaseInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author sarve
 */
@SpringBootApplication
@RestController
public class RaagatechApplication {

    @Autowired
    private OracleDatabaseInterface oracleDataSource;

    public static void main(String[] args) {
        SpringApplication.run(RaagatechApplication.class, args);
    }

    @RequestMapping("/test")
    public String home() throws Exception {
        String test_table_users = oracleDataSource.databaseConnectionTest();
        return "<h1>Spring Boot Hello World!</h1><br/>" + test_table_users;
    }
}
