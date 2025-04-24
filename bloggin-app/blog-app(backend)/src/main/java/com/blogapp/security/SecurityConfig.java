package com.blogapp.security;

import com.blogapp.config.AppConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig{
	
	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthEntryPoint;
	@Autowired
	private CustomUserDetailsService cusUserDetService;
	
	@Autowired
	private JwtAuthenticationFilter jwtFIlter;

    private final AppConfig appConfig;

    SecurityConfig(AppConfig appConfig) {
        this.appConfig = appConfig;
    }

	@Bean
	public PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}
	@Bean
	public AuthenticationProvider authProvider() {
		DaoAuthenticationProvider daoAuthProvider=new DaoAuthenticationProvider();
		daoAuthProvider.setPasswordEncoder(passwordEncoder());
		daoAuthProvider.setUserDetailsService(cusUserDetService);
		return daoAuthProvider;
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(c->c.disable()).
			exceptionHandling(ex->ex.authenticationEntryPoint(jwtAuthEntryPoint)).
			sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)).
			authorizeHttpRequests(auth->
			 auth.requestMatchers("/auth/**").permitAll() // Public endpoints
	            .requestMatchers("/api/admin/**").hasRole("ADMIN") // Admin-only endpoints
	           .requestMatchers("/api/comments/view/**").permitAll()
	           .requestMatchers("/api/post/**").hasRole("USER")
	           .requestMatchers("/api/posts/category/**").permitAll() // Public
	           .requestMatchers("/api/getcategories").permitAll() // Public
	           .requestMatchers(
	        		    "/v3/api-docs/**", 
	        		    "/swagger-ui/**", 
	        		    "/swagger-ui.html"
	        		).permitAll()

	           
	            .anyRequest().authenticated()). // Any other request needs authentication
			authenticationProvider(authProvider()).
			addFilterBefore(jwtFIlter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
			
	}
}