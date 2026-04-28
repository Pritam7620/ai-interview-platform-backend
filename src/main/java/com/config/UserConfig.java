package com.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.JWTsecurity.JwtFilter;

@Configuration
public class UserConfig {

	@Autowired
	private JwtFilter jwtFilter;
	
	@Bean
	static PasswordEncoder passEncoder() {

		return new BCryptPasswordEncoder();
		
	}
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

	    http.csrf(csrf -> csrf.disable())
	        .authorizeHttpRequests(authorize -> {
	        	authorize.requestMatchers("/users/register").permitAll();
	        	authorize.requestMatchers("/users/login").permitAll();
	        	authorize.requestMatchers("/resume/upload").permitAll();
	        	authorize.anyRequest().authenticated();
	        })
	        .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

	    return http.build();
	}
	
	
	
}
