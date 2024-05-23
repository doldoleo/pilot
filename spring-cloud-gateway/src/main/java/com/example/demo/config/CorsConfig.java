package com.example.demo.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.reactive.config.EnableWebFlux;

/**
 * WebFlux 기반 CorsConfigurationSource 필터 적용 
 */
@Configuration
@EnableWebFlux
public class CorsConfig {

	 @Bean
	 CorsWebFilter corsFilter() {
		 	CorsConfigurationSource source = corsConfigurationSource();
	        return new CorsWebFilter(source);
	    }
	     
	    @Bean
	    CorsConfigurationSource corsConfigurationSource() {
	    	CorsConfiguration config = new CorsConfiguration();
	    	
	        config.setAllowCredentials(true);
	        config.addAllowedOriginPattern("*");
	        config.addAllowedHeader("*");
	        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));

	    	UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	    	source.registerCorsConfiguration("/**", config);
	    	return source;
	    }
}