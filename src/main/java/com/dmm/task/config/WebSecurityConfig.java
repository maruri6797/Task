package com.dmm.task.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.formLogin(login -> login
				.loginPage("/login")
				.loginProcessingUrl("/login")
				.defaultSuccessUrl("/main")
				.failureUrl("/loginForm?error=true")
				.permitAll()
		).logout(logout -> logout
				.logoutSuccessUrl("/login")
		).authorizeHttpRequests(authz -> authz
				.requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
				.antMatchers("/login").permitAll()
				.anyRequest().authenticated()
		);
		return http.build();
	}
	
	@Bean
	public PasswordEncoder passwordEncodar() {
		return new BCryptPasswordEncoder();
	}
	
	public void configure(WebSecurity web) throws Exception {
		web.debug(false).ignoring().antMatchers("/images/**", "/js/**", "/css/**");
	}
}
