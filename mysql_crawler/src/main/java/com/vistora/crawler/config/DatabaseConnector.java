package com.vistora.crawler.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
@Component
public class DatabaseConnector {

    //  Injecting values from application.properties
	

	    @Value("${spring.datasource.url}")
	    private String dbUrl;

	    @Value("${spring.datasource.username}")
	    private String dbUsername;

	    @Value("${spring.datasource.password}")
	    private String dbPassword;

	    public Connection getConnection() throws SQLException {
	        return DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
	    }
	}



