package com.medeil.authservice.config;

import java.util.List;

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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.medeil.authservice.jwt.JwtAuthenticationFilter;
import com.medeil.authservice.security.JwtAccessDeniedHandler;
import com.medeil.authservice.security.JwtAuthenticationEntryPoint;
import com.medeil.authservice.service.CustomUserDetailsService;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
	
	private final JwtAuthenticationFilter jwtAuthenticationFilter;
	
	private final CustomUserDetailsService customUserDetailsService;
	
	private final JwtAuthenticationEntryPoint authenticationEntryPoint;

	private final JwtAccessDeniedHandler accessDeniedHandler;
	
	    @Bean
	    PasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
	    }
	    
	    @Bean
	    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	    	
	    	http
	        .csrf(csrf -> csrf.disable())
	        .authenticationProvider(authenticationProvider())
	        .sessionManagement(session ->
	                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
	        .exceptionHandling(ex -> ex
	                .authenticationEntryPoint(authenticationEntryPoint)
	                .accessDeniedHandler(accessDeniedHandler))
	        .authorizeHttpRequests(auth -> auth
	                .requestMatchers(
	                        //"/api/auth/**",
	                		"/api/auth/login",
	                		"/api/auth/register",
	                		"/api/auth/refresh",
	                        "/swagger-ui/**",
	                        "/swagger-ui.html",
	                        "/api-docs/**",
	                        "/v3/api-docs/**")
	                .permitAll()
	                .anyRequest()
	                .authenticated())
	        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

	        return http.build();
	    }
	    
	    

		@Bean
		public CorsConfigurationSource corsConfigurationSource() {
		    CorsConfiguration config = new CorsConfiguration();
		    config.setAllowedOrigins(List.of("*"));
		    config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
		    config.setAllowedHeaders(List.of("*"));
		    config.setExposedHeaders(List.of("Authorization"));

		    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		    source.registerCorsConfiguration("/**", config);
		    return source;
		}
		
		@Bean
		AuthenticationProvider authenticationProvider() {

		    DaoAuthenticationProvider provider =
		            new DaoAuthenticationProvider();

		    provider.setUserDetailsService(customUserDetailsService);
		    provider.setPasswordEncoder(passwordEncoder());

		    return provider;
		}

}
