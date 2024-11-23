package com.example.demo.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

@Configuration
public class CustomConfig {

	@Order(1)
	@Bean
	public SecurityFilterChain chain(HttpSecurity http) throws Exception {
		OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);
		http.getConfigurer(OAuth2AuthorizationServerConfigurer.class).oidc(Customizer.withDefaults());
		http.exceptionHandling(cust -> cust.authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login")));
		return http.build();
	}

	@Order(2)
	@Bean
	public SecurityFilterChain http(HttpSecurity http) throws Exception {
		http.formLogin(Customizer.withDefaults());
		http.authorizeHttpRequests(
				cust -> cust.requestMatchers("/oauth2/token").permitAll().anyRequest().authenticated());
//		http.sessionManagement(cust -> cust.sessionCreationPolicy(SessionCreationPolicy.ALWAYS));
		return http.build();
	}

	@Bean
	public UserDetailsService detailsService() {
		return new InMemoryUserDetailsManager(
				User.withUsername("admin").password("admin").authorities("ADMIN").build());
	}

	@Bean
	public PasswordEncoder encoder() {
		return NoOpPasswordEncoder.getInstance();
	}

	@Bean
	public RegisteredClientRepository clientRepository() {

		var client1 = RegisteredClient.withId("AUTH_CODE")
				.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
				.authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN).scope("openid")
				.clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
				.clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_POST).clientId("client")
				.clientSecret("secret").redirectUri("http://localhost:9090/login/oauth2/code/client").build();

		var client2 = RegisteredClient.withId("CLIENT-CREDENTIAL")
				.authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
				.authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN).scope("openid")
				.clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
				.clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_POST).clientId("client2")
				.clientSecret("secret2").redirectUri("http://localhost:9090/login/oauth2/code/client2").build();
		return new InMemoryRegisteredClientRepository(client1, client2);
	}
}
