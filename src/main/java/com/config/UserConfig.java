package com.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class UserConfig {

	@Bean
	static PasswordEncoder passEncoder() {

		return new BCryptPasswordEncoder();
		
	}
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) {
		
		http.csrf(csrf->csrf.disable())
		.authorizeHttpRequests(authorize ->{
			authorize.requestMatchers(HttpMethod.POST , "/users/register").permitAll();
			authorize.requestMatchers(HttpMethod.POST, "/users/login").permitAll();
			authorize.requestMatchers(HttpMethod.POST, "/users/register").permitAll();
			authorize.anyRequest().authenticated();
			
			
		});
		
		
		return http.build();
	}
	
	
	
}
