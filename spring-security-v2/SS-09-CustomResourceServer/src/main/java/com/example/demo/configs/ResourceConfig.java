package com.example.demo.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class ResourceConfig {

	@Bean
	public SecurityFilterChain chain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(cust -> cust.anyRequest().authenticated());
		http.oauth2ResourceServer(cust -> cust.jwt(jwtCust -> jwtCust.jwkSetUri("http://localhost:8080/oauth2/jwks")));
		return http.build();
	}
}
