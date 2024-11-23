package com.ss_05.demo;

import java.util.List;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationManager implements AuthenticationManager {

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {

		var username = authentication.getName();
		var password = authentication.getCredentials().toString();

		if (username.equalsIgnoreCase("admin") && password.equalsIgnoreCase("secret")) {
			System.out.println("User is authenticated...");
			return new UsernamePasswordAuthenticationToken(username, password, List.of());
		} else {
			throw new BadCredentialsException("Username or password is incorrect");
		}
	}

}
