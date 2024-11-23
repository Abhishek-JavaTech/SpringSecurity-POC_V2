package com.example.demo.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@EnableMethodSecurity
@Configuration
public class Configs {

	@Bean
	public SecurityFilterChain security(HttpSecurity http) throws Exception {

		http.formLogin(Customizer.withDefaults());
		http.authorizeHttpRequests(config -> config.anyRequest().authenticated());

		return http.build();
	}

	@Bean
	public JdbcUserDetailsManager detailsManager(DataSource dataSource) {
		return new JdbcUserDetailsManager(dataSource);
	}

	@Bean
	public DataSource dataSource() {

		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setUrl("jdbc:mysql://localhost:3306/ss-poc");
		dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
		dataSource.setUsername("root");
		dataSource.setPassword("changeme");

		Properties properties = new Properties();
		properties.put("spring.jpa.hibernate.ddl-auto", "update");
		properties.put("spring.jpa.show-sql", "true");
		properties.put("spring.jpa.database-platform", "org.hibernate.dialect.MySQLDialect");

		dataSource.setConnectionProperties(properties);
		return dataSource;
	}
}
