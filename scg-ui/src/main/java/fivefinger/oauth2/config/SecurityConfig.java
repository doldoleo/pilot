package fivefinger.oauth2.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import fivefinger.oauth2.handler.CustomLogoutHandler;
import fivefinger.oauth2.handler.OAuth2AuthenticationFailureHandler;
import fivefinger.oauth2.handler.OAuth2AuthenticationSuccessHandler;
import fivefinger.oauth2.repository.HttpCookieOAuth2AuthorizationRequestRepository;
import fivefinger.oauth2.service.CustomOAuth2UserService;
import fivefinger.oauth2.service.CustomOidcUserService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	// 커스텀한 OAuth2UserService DI.
	@Autowired
	private CustomOAuth2UserService customOAuth2UserService;
	@Autowired
	private CustomOidcUserService customOidcUserService;
	@Autowired
	private  OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
	@Autowired
	private OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;
	@Autowired
	private HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;
	
	@Autowired
	private CustomLogoutHandler customLogoutHandler;
	
	// encoder를 빈으로 등록.
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/**
	 * 
	 * @return
	 */
	@Bean
	WebSecurityCustomizer webSecurityCustomizer() {
		return web -> web.debug(false).ignoring().requestMatchers("/css/**", "/img/**");
	}


	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf(AbstractHttpConfigurer::disable);
		
		http.authorizeHttpRequests(authorize -> {
			authorize.requestMatchers("/").permitAll()
			         .requestMatchers("/login").permitAll()
					.anyRequest().authenticated();
		});
		
		http.formLogin(configurer -> { // 📌 추가
			configurer.loginPage("/login").permitAll();
			configurer.defaultSuccessUrl("/main"); // "성공시 /main으로 이동.
		});
		
		// oauth2 로그인에 성공하면, 유저 데이터를 가지고 우리가 생성한 customOAuth2UserService에서 처리를 하겠다.
		http.oauth2Login(oauth2Login -> {
			oauth2Login.authorizationEndpoint(
					      authEndpoint -> 
					          authEndpoint.authorizationRequestRepository(httpCookieOAuth2AuthorizationRequestRepository))
					   .userInfoEndpoint(
					      userInfoEndpoint -> {
						      userInfoEndpoint.userService(customOAuth2UserService);
						      userInfoEndpoint.oidcUserService(customOidcUserService);
						  })
					   .successHandler(oAuth2AuthenticationSuccessHandler)
					   .failureHandler(oAuth2AuthenticationFailureHandler);
		   });
		
		http.logout(configurer -> { // 📌 추가
			configurer.logoutUrl("/logout");
			configurer.addLogoutHandler(customLogoutHandler);
			configurer.logoutSuccessUrl("/").permitAll(); // 로그아웃에 대해서 성공하면 "/"로 이동
		});

		http.oidcLogout((logout) -> logout.backChannel(Customizer.withDefaults()));
		return http.build();
	}
	
}
