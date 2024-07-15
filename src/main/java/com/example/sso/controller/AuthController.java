package com.example.sso.controller;

import com.example.sso.model.LoginRequest;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class AuthController {

	private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
		String token = UUID.randomUUID().toString();
		Cookie cookie = new Cookie("auth_token", token);
		cookie.setHttpOnly(true);
		cookie.setSecure(true); // Set to true for HTTPS
		cookie.setPath("/");
		cookie.setMaxAge(3600); // 1 hour
		response.addCookie(cookie);

		// Manually add SameSite attribute to the cookie
		response.setHeader("Set-Cookie",
				String.format("auth_token=%s; Path=/; HttpOnly; Secure; SameSite=None", token));

		logger.info("Login successful, token set: {}", token);

		return ResponseEntity.ok("Zalogowano pomyślnie");
	}

	@GetMapping("/validate-token")
	public ResponseEntity<String> validateToken(@CookieValue(name = "auth_token", required = false) String token) {
		logger.info("Validating token, received token: {}", token);

		if (token != null) {
			return ResponseEntity.ok("Token is valid");
		} else {
			logger.warn("Token is missing or invalid");
			return ResponseEntity.status(401).body("Token is missing or invalid");
		}
	}

	@GetMapping("/set-token")
	public ResponseEntity<String> setToken(HttpServletResponse response) {
		String token = UUID.randomUUID().toString();
		Cookie cookie = new Cookie("auth_token", token);
		cookie.setHttpOnly(true);
		cookie.setSecure(true); // Set to true for HTTPS
		cookie.setPath("/");
		cookie.setMaxAge(3600); // 1 hour
		response.addCookie(cookie);

		// Manually add SameSite attribute to the cookie
		response.setHeader("Set-Cookie",
				String.format("auth_token=%s; Path=/; HttpOnly; Secure; SameSite=None", token));

		logger.info("Token set: {}", token);

		return ResponseEntity.ok("Token set successfully");
	}
}
