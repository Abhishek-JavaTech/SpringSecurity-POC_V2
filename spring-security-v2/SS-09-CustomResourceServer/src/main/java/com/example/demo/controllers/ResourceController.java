package com.example.demo.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/res")
public class ResourceController {

	@GetMapping
	public String get() {
		return "Hello World from resource server!";
	}

}
