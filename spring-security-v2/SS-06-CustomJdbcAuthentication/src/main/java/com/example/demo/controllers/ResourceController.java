package com.example.demo.controllers;

import org.springframework.context.annotation.Bean;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class ResourceController {

	@PreAuthorize("hasAuthority('WRITE')")
	@GetMapping("/admin")
	public String getAdmin(Authentication authentication) {
		return "Authenticated ADMIN user : " + authentication.getName();
	}

	@PreAuthorize("hasAnyAuthority('WRITE', 'READ')")
	@GetMapping("/user")
	public String getUser(Authentication authentication) {
		return "Authenticated USER : " + authentication.getName();
	}

	@PreAuthorize("hasAuthority('SECRET')")
	@GetMapping("/secret")
	public String getSecret(Authentication authentication) {
		return "User logged in successfully! " + authentication.getName();
	}

	@GetMapping
	public String get(Authentication authentication) {
		return "User logged in successfully! " + authentication.getName();
	}

	@Bean
	public PasswordEncoder encoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

}
