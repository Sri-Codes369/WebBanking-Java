package com.ibank.demo.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibank.demo.dto.UserDTO;
import com.ibank.demo.dto.UserFetchDTO;
import com.ibank.demo.service.UserService;

import java.util.Date;
import java.util.HashMap;
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
                @SuppressWarnings("deprecation")
                String jwt = Jwts.builder()
                        .setSubject(credential)
                        .claim("user", jsonArray)
                        .setIssuedAt(new Date())
                        .setExpiration(new Date(System.currentTimeMillis() + 600000)) // 1 hour expiration
                        .signWith(SignatureAlgorithm.HS256, secretKey)
                        .compact();

                // Create a cookie
                Cookie cookie = new Cookie("Auth_Token", jwt);
                cookie.setHttpOnly(false); // Change to false for debugging only
                cookie.setMaxAge(600); // 1 hour expiration
                cookie.setPath("/"); // Available for the entire application
                cookie.setSecure(false); // Set to true if using HTTPS           
                response.addCookie(cookie);
               
                
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


    @SuppressWarnings("deprecation")
    @PostMapping("/extend-token")
public ResponseEntity<?> extendToken(HttpServletRequest request, HttpServletResponse response) {
    try {
        // Retrieve the cookies from the request
        Cookie[] cookies = request.getCookies();
        String token = null;

        // Find the 'Auth_Token' cookie
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("Auth_Token".equals(cookie.getName())) {
                    token = cookie.getValue();
                    break;
                }
            }
        }

        if (token == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("{\"error\":\"No token provided\"}");
        }

        // Validate and parse the existing token
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();

        // Check if the token is near expiration (e.g., within 1 minute)
        long expirationTime = claims.getExpiration().getTime();
        long currentTime = System.currentTimeMillis();

        if (expirationTime - currentTime > 60000) {
            // Token is not yet close to expiration
            return ResponseEntity.ok("Token does not need to be refreshed yet");
        }

        // Re-issue the token with the same claims and a new 10-minute expiration
        String newToken = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 600000)) // Extend for 10 minutes
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();

        // Set the new token in the cookie
        Cookie newAuthCookie = new Cookie("Auth_Token", newToken);
        newAuthCookie.setHttpOnly(true);
        newAuthCookie.setMaxAge(600); // 10 minutes
        newAuthCookie.setPath("/");
        response.addCookie(newAuthCookie);

        return ResponseEntity.ok("Token extended");

    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("{\"error\":\"Invalid token\"}");
    }
}


   @PostMapping("/register")
public ResponseEntity<Map<String, Object>> registerUser(@RequestBody UserDTO userDTO) {
    Map<String, Object> response = new HashMap<>();
    try {
        int resultCode = userService.registerUser(userDTO);

        switch (resultCode) {
            case -1:
                response.put("ResponseCode", -1);
                response.put("Response", "User Name Already Exists...!");
                return ResponseEntity.status(HttpStatus.OK).body(response);

            case -2:
                response.put("ResponseCode", -2);
                response.put("Response", "Email Already Exists...!");
                return ResponseEntity.status(HttpStatus.OK).body(response);

            case -3:
                response.put("ResponseCode", -3);
                response.put("Response", "Phone Number Already Exists...!");
                return ResponseEntity.status(HttpStatus.OK).body(response);

            case 0:
                response.put("ResponseCode", 0);
                response.put("Response", "User Registered Successfully");
                return ResponseEntity.status(HttpStatus.OK).body(response);

            default:
                response.put("ResponseCode", -99);
                response.put("Response", "Unexpected error occurred");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    } catch (Exception e) {
        response.put("ResponseCode", -500);
        response.put("Response", "Error registering user: " + e.getLocalizedMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}


 @GetMapping("/users")
    public ResponseEntity<List<UserFetchDTO>> getAllUsers() {
        List<UserFetchDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }
}
