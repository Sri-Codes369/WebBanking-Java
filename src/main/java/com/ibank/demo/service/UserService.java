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

    @Value("${AES.secret}")
    private String AES_SecretKey;

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
                String encryptedPassword = AESUtil.encrypt(password, AES_SecretKey);
            stmt.setString(1, credential);
            stmt.setString(2, encryptedPassword );
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
        
        }catch (Exception e) {
            logger.error("Error encrypting data", e);
            throw new RuntimeException("Error encrypting data", e);
        }
        return user;
    };

    @Transactional
    public Integer registerUser(UserDTO userDTO) {
        try {
            // Encrypt the user details using the AES secret key
           userDTO.getUserName();
           String encryptedPassword = AESUtil.encrypt(userDTO.getPassword(), AES_SecretKey);
             userDTO.getFirstName();
             userDTO.getLastName();
             userDTO.getEmail();
             userDTO.getPhone();

             logger.info(encryptedPassword);

            // Call the stored procedure with encrypted values
            return userRepository.spRegisterUser(
                userDTO.getUserName(),
                    encryptedPassword,
                    userDTO.getFirstName(),
             userDTO.getLastName(),
             userDTO.getEmail(),
             userDTO.getPhone()
            );
        } catch (Exception e) {
            logger.error("Error encrypting data", e);
            throw new RuntimeException("Error encrypting data", e);
        }
    }

};
