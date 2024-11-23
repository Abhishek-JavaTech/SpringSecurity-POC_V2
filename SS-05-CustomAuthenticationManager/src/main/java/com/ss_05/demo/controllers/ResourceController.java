package com.ss_05.demo.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ss")
public class ResourceController {

	@GetMapping
	public String get(Authentication authentication) {
		return "Hello World! Let's Welcome - " + authentication.getName();
	}

}
