package com.ibank.demo.service;

import org.slf4j.Logger;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.SecurityException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ibank.demo.controller.UserController;


@Service
public class UserService {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	
	@Value("${jwt.secret}")
    private String secretKey;

    @Value("${spring.datasource.url}")
    private String dbUrl;

    @Value("${spring.datasource.username}")
    private String dbUsername;

    @Value("${spring.datasource.password}")
    private String dbPassword;



    public List<String> login(String credential, String password) throws SQLException {
        List<String> user = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
             CallableStatement stmt = connection.prepareCall("{call spUserLogin(?, ?)}")) {

            stmt.setString(1, credential);
            stmt.setString(2, password);
           logger.info(credential);
           logger.info(password);
            logger.info(stmt+"stmt");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String jsonString = rs.getString(1);
//                    if(jsonString == null) {
//                    	 logger.info(jsonString);
//                    }
//                   
                    if (jsonString != null && !jsonString.isEmpty()) {
                    	logger.info(jsonString);
                        user.add(jsonString);
                    }
                }
            }
        
        }
        return user;
    }
}
