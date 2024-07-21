package com.example.sso.controller;

import com.example.sso.model.LoginRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        String token = UUID.randomUUID().toString();
        
        // Set the cookie
        String cookieString = String.format("auth_token=%s; Path=/; HttpOnly; Secure; SameSite=None; Partitioned", token);
        response.setHeader("Set-Cookie", cookieString);

        // Log the cookie being set
        logger.info("Setting cookie: {}", cookieString);

        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("message", "Zalogowano pomyślnie");
        
        return ResponseEntity.ok(responseBody);
    }

    @GetMapping("/validate-token")
    public ResponseEntity<Map<String, String>> validateToken(@CookieValue(name = "auth_token", required = false) String token) {
        logger.info("Validating token, received token: {}", token);

        Map<String, String> responseBody = new HashMap<>();

        if (token != null) {
            responseBody.put("message", "Token is valid");
            return ResponseEntity.ok(responseBody);
        } else {
            responseBody.put("error", "Token is missing or invalid");
            return ResponseEntity.status(401).body(responseBody);
        }
    }

	@GetMapping("/set-token")
	public ResponseEntity<Map<String, String>> setToken(HttpServletResponse response) {
    String token = UUID.randomUUID().toString();
    
    // Set the cookie
    String cookieString = String.format("auth_token=%s; Path=/; HttpOnly; Secure; SameSite=None; Partitioned", token);
    response.setHeader("Set-Cookie", cookieString);
    
    // Log the cookie being set
    logger.info("Setting cookie: {}", cookieString);
    
    Map<String, String> responseBody = new HashMap<>();
    responseBody.put("message", "Token set successfully");
    
    return ResponseEntity.ok(responseBody);
}
}