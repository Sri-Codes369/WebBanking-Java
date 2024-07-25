package com.ibank.demo.service;

import org.slf4j.Logger;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.ibank.demo.controller.UserController;
import com.ibank.demo.dto.UserDTO;
import com.ibank.demo.repository.UserRepository;


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

    @Autowired
    private UserRepository userRepository;


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
                    if (jsonString != null && !jsonString.isEmpty()) {
                    	logger.info(jsonString);
                        user.add(jsonString);
                    }
                }
            }
        
        }
        return user;
    };

    @Transactional
    public int registerUser(UserDTO userDTO) {
   
        
      return userRepository.spRegisterUser(
            userDTO.getUserName(),
            userDTO.getPassword(),
            userDTO.getFirstName(),
            userDTO.getLastName(),
            userDTO.getEmail(),
            userDTO.getPhone(),
            userDTO.getAddress()
         
        );

    }

};
