package com.ibank.demo.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibank.demo.dto.UserDTO;
import com.ibank.demo.service.UserService;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
// @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Value("${jwt.secret}")
    private String secretKey;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginRequest,
                                   HttpServletResponse response) {
        try {
            String credential = loginRequest.get("credential");
            String password = loginRequest.get("password");

            List<String> user = userService.login(credential, password);

            if (user != null && !user.isEmpty()) {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonArray = objectMapper.readTree("[" + String.join(",", user) + "]");

                // Generate JWT and store in browser cookie
                String jwt = Jwts.builder()
                        .setSubject(credential)
                        .claim("user", jsonArray)
                        .setIssuedAt(new Date())
                        .setExpiration(new Date(System.currentTimeMillis() + 3600000)) // 1 hour expiration
                        .signWith(SignatureAlgorithm.HS256, secretKey)
                        .compact();

                // Create a cookie
                Cookie cookie = new Cookie("Auth_Token", jwt);
                cookie.setHttpOnly(false); // Change to false for debugging only
                cookie.setMaxAge(3600); // 1 hour expiration
                cookie.setPath("/"); // Available for the entire application
                cookie.setSecure(false); // Set to true if using HTTPS
                cookie.setDomain(""); // Default to current domain
                cookie.setAttribute("SameSite", "Lax"); // or "Strict" or "None" based on your needs
                response.addCookie(cookie);
                logger.info("Login successful");
                
                return ResponseEntity.ok("Login successful");
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("{\"error\":\"Invalid username/email or password\"}");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    };


    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserDTO userDTO) {
        try {
          int resultCode  =  userService.registerUser(userDTO);
        
          switch (resultCode) {
            case -1:
                return ResponseEntity.status(200).body(resultCode+"");
            case -2:
            return ResponseEntity.status(200).body(resultCode+"");
            case 0:
            return ResponseEntity.status(200).body(resultCode+"");
            default:
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error occurred");
        }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error registering user: " + e.getLocalizedMessage());
        }
    };
}
