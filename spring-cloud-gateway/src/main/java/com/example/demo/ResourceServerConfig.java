package com.example.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoders;
import org.springframework.security.web.server.SecurityWebFilterChain;


/**
 * Webflux 기반 Security 설정 방법
 */
@Configuration
@EnableWebFluxSecurity
public class ResourceServerConfig {
	@Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
	private String issuerUri;

    @Bean
    SecurityWebFilterChain  securityFilterChain(ServerHttpSecurity http) throws Exception {
    	 
    	 http
         .authorizeExchange(exchanges -> exchanges
             .anyExchange().authenticated()
         )
         .oauth2ResourceServer((oauth2) -> oauth2.jwt(Customizer.withDefaults()))
         ;
     return http.build();
    }
    
    @Bean
    ReactiveJwtDecoder jwtDecoder() {
        return ReactiveJwtDecoders.fromIssuerLocation(issuerUri);
    }
}