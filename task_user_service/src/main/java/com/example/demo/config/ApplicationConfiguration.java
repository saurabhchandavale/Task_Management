package com.example.demo.config;

import java.beans.Customizer;
import java.util.Arrays;
import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.boot.autoconfigure.integration.IntegrationProperties.Management;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import jakarta.servlet.http.HttpServletRequest;

@Configuration
public class ApplicationConfiguration {
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
		/*http.sessionManagement(
				management.management.sessionCreationPolicy(
					SessionCreationPolicy.STATELESS
				)
		).authorizehHttpRequests(
				Authorize -> Authorize.requestMatchers("/api/**").authenticated.anyRequest().permitAll()
		).addFilterBefore(new JwtTokenValidator(), BasicAuthenticationFilter.class)
		.csrf(csrf-> csrf.disable())
		.cors(cors->cors.configurationSource(corsConfigurationSource()))
		.httpBasic(Customizer.withDefaults())
		.formLogin(Customizer.withDefaults());*/
		
      http
      .sessionManagement()
          .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
          .and()
      .authorizeRequests()
          .requestMatchers("/api/**").authenticated()
          .anyRequest().permitAll()
          .and()
      .addFilterBefore(new JwtTokenValidator(), BasicAuthenticationFilter.class)
      .csrf().disable()
      .cors().configurationSource(corsConfigurationSource())
      .and()
      .httpBasic().and()
      .formLogin();
	
		return http.build();
	}
	
	private CorsConfigurationSource corsConfigurationSource() {
		return new CorsConfigurationSource() {
			
			@Override
			public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
				CorsConfiguration cfg = new CorsConfiguration();
				cfg.setAllowedOrigins(Collections.singletonList("*"));
				cfg.setAllowedMethods(Collections.singletonList("*"));
				cfg.setAllowedHeaders(Collections.singletonList("*"));
				cfg.setMaxAge(3600L);
				cfg.setExposedHeaders(Arrays.asList("Authorization"));
				cfg.setAllowCredentials(true);
						
				return cfg;
			}
		};
	}
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}

//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//
//@Configuration
//@EnableWebSecurity
//public class ApplicationConfiguration extends WebSecurityConfigurerAdapter {
//    
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//            .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//            .authorizeRequests()
//                .antMatchers("/api/**").authenticated()
//                .anyRequest().permitAll()
//                .and()
//            .addFilterBefore(null, BasicAuthenticationFilter.class)
//            .csrf().disable()
//            .cors().configurationSource(corsConfigurationSource())
//            .and()
//            .httpBasic().and()
//            .formLogin();
//    }
//
//    // Define your CorsConfigurationSource bean here if needed
//    // private CorsConfigurationSource corsConfigurationSource() { ... }
//}

