package com.example.sso.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity

public class CorsConfig {

	@Bean
	public CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		config.addAllowedOrigin("http://localhost:4200");
		config.addAllowedOrigin("http://localhost:8443");
		config.addAllowedOrigin("http://127.0.0.1:4200");
		config.addAllowedOrigin("https://127.0.0.1:8443");
		config.addAllowedHeader("*");
		config.addAllowedMethod("*");
		config.addExposedHeader("Set-Cookie");
		source.registerCorsConfiguration("/**", config);
		return new CorsFilter(source);
	}
}
