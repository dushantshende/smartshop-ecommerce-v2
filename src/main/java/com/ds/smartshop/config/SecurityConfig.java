package com.ds.smartshop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.ds.smartshop.security.jwt.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http,
			JwtAuthenticationFilter jwtAuthenticationFilter)throws Exception {
		
		http
		.csrf(csrf-> csrf.disable())
		.sessionManagement(session-> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
		
		.authorizeHttpRequests(auth->auth
		//login and registration are public
		.requestMatchers("/api/auth/**").permitAll()
		
		//Product viewing is public 
		.requestMatchers(
				org.springframework.http.HttpMethod.GET,
				"/api/products",
				"/api/products/**").permitAll()
		
		//only ADMIN can modify products
		.requestMatchers(
				org.springframework.http.HttpMethod.POST,
				"/api/products",
				"/api/products/**").hasRole("ADMIN")
		
		.requestMatchers(
				org.springframework.http.HttpMethod.PUT,
				"/api/products",
				"/api/products/**").hasRole("ADMIN")
		
		.requestMatchers(
				org.springframework.http.HttpMethod.DELETE,
				"/api/products",
				"/api/products/**"	).hasRole("ADMIN")
		
		//All other endpoints require authentication
		.anyRequest().authenticated())
		
		.addFilterBefore(jwtAuthenticationFilter,UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}
}
