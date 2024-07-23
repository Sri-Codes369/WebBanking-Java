package com.ibank.demo.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

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
import com.ibank.demo.service.UserService;

@RestController
@RequestMapping("/api")
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
                cookie.setHttpOnly(true);
                cookie.setMaxAge(3600); // 1 hour expiration
                cookie.setPath("/"); // Cookie will be available for the entire application
                
                // Add cookie to the response
                response.addCookie(cookie);
                
                return ResponseEntity.status(HttpStatus.OK).body(cookie);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("{\"error\":\"Invalid username/email or password\"}");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }
}
