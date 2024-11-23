package com.ss_03.demo.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ss")
public class ResourceController {

	@GetMapping
	public String get() {
		return "Hello World!";
	}

}
