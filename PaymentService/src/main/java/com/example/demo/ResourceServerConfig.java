package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class ResourceServerConfig {
	
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    	http
    	.securityMatcher("/api/v1/payment/**")
    	.authorizeHttpRequests((authorize) -> authorize.anyRequest().authenticated())
		.oauth2ResourceServer((oauth2) -> oauth2.jwt(Customizer.withDefaults()))
		;
    	return http.build();
    }
    
    /**
     * 아래와 같이 범위를 지정하고 권한 부여 요청시 인가서버에 지정했던 scope가 리소스 서버의 권한 범위에 포함하지 않으면 접근이 거부된다. 
     * SCOPE_
     * 
     *  @Bean
    SecurityFilterChain securityFilterChain1(HttpSecurity http) throws Exception {
        http.antMatcher("/photos/1").authorizeRequests(
                (requests) -> requests.antMatchers(HttpMethod.GET, "/photos/1")
                .hasAuthority("SCOPE_photo")
                .anyRequest().authenticated());
        http.oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);
        return http.build();
    }

   @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.securityMatcher("/articles/**")
          .authorizeHttpRequests(authorize -> authorize.anyRequest()
            .hasAuthority("SCOPE_articles.read"))
          .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));
        return http.build();
    }
     */
}