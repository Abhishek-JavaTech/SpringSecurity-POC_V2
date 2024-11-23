package com.ss_03.demo.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

	@Bean
	public SecurityFilterChain chain(HttpSecurity http) throws Exception {

		http.formLogin(Customizer.withDefaults());
		http.authorizeHttpRequests(conf -> conf.anyRequest().authenticated());

		return http.build();
	}

	@Bean
	public UserDetailsService detailsService() {
		var user = User.withUsername("admin")
				.password("{bcrypt}$2a$12$.28zd8NcnuyfUUvXHoPhW.qF7gexclJSXP3SB/Da6jKc0KM54na4.")
				.authorities("read", "admin").build();

		return new InMemoryUserDetailsManager(user);
	}

	@Bean
	public PasswordEncoder encoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
}
