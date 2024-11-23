package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClientController {

	@Autowired
	private OAuth2AuthorizedClientService authService;

	@GetMapping
	public String get(Authentication authentication) {

		if (authentication instanceof OAuth2AuthenticationToken oauth2Token) {
			OAuth2AuthorizedClient authUser = authService
					.loadAuthorizedClient(oauth2Token.getAuthorizedClientRegistrationId(), oauth2Token.getName());
			return "AccessToken - " + authUser.getAccessToken().getTokenValue() + " \n " + " RefreshToken - "
//					+ authUser.getRefreshToken().getTokenValue() + " \n " 
					+ "IdToken " + " \n" + (oauth2Token.getPrincipal());
		}

		return "Hello World!";
	}
}
