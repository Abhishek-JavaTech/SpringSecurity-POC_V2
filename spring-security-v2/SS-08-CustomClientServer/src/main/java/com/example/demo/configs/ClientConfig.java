package com.example.demo.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProvider;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProviderBuilder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ClientConfig {

	@Bean
	public SecurityFilterChain chain(HttpSecurity http) throws Exception {

		http.formLogin(Customizer.withDefaults());

		http.oauth2Login(Customizer.withDefaults());

		http.authorizeHttpRequests(cust -> cust.anyRequest().authenticated());

		return http.build();
	}

	/*
	 * @Bean public OAuth2AuthorizedClientManager authorizedClientManager(
	 * ClientRegistrationRepository clientRegistrationRepository,
	 * OAuth2AuthorizedClientRepository authorizedClientRepository) {
	 * 
	 * OAuth2AuthorizedClientProvider authorizedClientProvider =
	 * OAuth2AuthorizedClientProviderBuilder.builder()
	 * .authorizationCode().refreshToken().clientCredentials().build();
	 * 
	 * DefaultOAuth2AuthorizedClientManager authorizedClientManager = new
	 * DefaultOAuth2AuthorizedClientManager( clientRegistrationRepository,
	 * authorizedClientRepository);
	 * authorizedClientManager.setAuthorizedClientProvider(authorizedClientProvider)
	 * ;
	 * 
	 * return authorizedClientManager; }
	 */

	
	@Bean
	public ClientRegistration github() {
		var github = CommonOAuth2Provider.GITHUB.getBuilder("GITHUB").clientId("Ov23li8zjdlWXz8MLbIG")
				.clientSecret("aba3d42ce6c0fef56d5607b7a3c7e050cb1f41af").build();

		return github;
	}
	
	
	@Bean
	public ClientRegistrationRepository clientRegistrationRepository() {

		var client1 = ClientRegistration.withRegistrationId("AUTH_CODE")
				.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
//				.authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
				.clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
				.clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_POST)
				.redirectUri("http://localhost:9090/login/oauth2/code/client")
				.authorizationUri("http://127.0.0.1:8080/oauth2/authorize")
				.tokenUri("http://127.0.0.1:8080/oauth2/token").issuerUri("http://127.0.0.1:8080")
				.jwkSetUri("http://127.0.0.1:8080/oauth2/jwks").clientId("client").clientSecret("secret")
				.scope("openid").build();

		var client2 = ClientRegistration.withRegistrationId("CLIENT-CREDENTIAL").clientId("client2").clientSecret("secret2")
				.authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
				.clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
				.clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_POST)
				.issuerUri("http://127.0.0.1:8080").redirectUri("http://localhost:9090/login/oauth2/code/client2")
				.authorizationUri("http://127.0.0.1:8080/oauth2/authorize")
				.tokenUri("http://127.0.0.1:8080/oauth2/token").jwkSetUri("http://127.0.0.1:8080/oauth2/jwks")
				.scope(OidcScopes.OPENID).build();
		return new InMemoryClientRegistrationRepository(client1, client2, github());

	}
}
