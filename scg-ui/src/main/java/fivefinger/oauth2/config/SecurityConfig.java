package fivefinger.oauth2.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import fivefinger.oauth2.handler.OAuth2AuthenticationFailureHandler;
import fivefinger.oauth2.handler.OAuth2AuthenticationSuccessHandler;
import fivefinger.oauth2.repository.HttpCookieOAuth2AuthorizationRequestRepository;
import fivefinger.oauth2.service.CustomOAuth2UserService;
import fivefinger.oauth2.service.CustomOidcUserService;
import fivefinger.oauth2.service.OAuth2UserPrincipal;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {
//	@Autowired
//	private RestTemplate restTemplate;

	
	@Value("${spring.security.oauth2.client.registration.komsco.client-id}")
	private String clientId;
	@Value("${spring.security.oauth2.client.registration.komsco.client-secret}")
	private String clientSecret;
	
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
		
		http.logout(configurer -> { // 📌 추가
			configurer.logoutUrl("/logout");
			configurer.addLogoutHandler(new CustomLogoutHandler());
			configurer.invalidateHttpSession(true);
			configurer.logoutSuccessUrl("/").permitAll(); // 로그아웃에 대해서 성공하면 "/"로 이동
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

		return http.build();
	}
	
	/**
	 * 소셜로그인 code 삭제
	 */
	public class CustomLogoutHandler implements LogoutHandler {
		@Override
		public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
			try {
				request.getSession().invalidate();
				Object principal = authentication.getPrincipal();
		        if (principal instanceof OAuth2UserPrincipal) {
		        	revokeToken(((OAuth2UserPrincipal) principal).getOAuth2UserInfo().getAccessToken());
		        	
		        }
			} catch (Exception e) {
				e.printStackTrace();
			}	
				
		}
		
//		private void revokeToken(String accessToken) {
//	      MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
//	      params.add("token_type_hint", "access_token");
//	      params.add("client_id", clientId);
//	      params.add("client_secret", clientSecret);
//	      params.add("token", accessToken);
//	
//	      HttpHeaders headers = new HttpHeaders();
//	      headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//	
//	      HttpEntity<?> entity = new HttpEntity<>(params, headers);
//	      restTemplate.exchange("http://auth.5finger.co.kr:9000/oauth2/revoke", HttpMethod.POST, entity, Void.class);
//	  }
		
		
		private void revokeToken(String accessToken) {
	        LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

	        map.add("token_type_hint", "access_token");
	        map.add("token", accessToken);
	        map.add("client_id", clientId);
	        map.add("client_secret", clientSecret);

	        WebClient revokeTokenWebClient = WebClient.builder()
	                .baseUrl("http://auth.5finger.co.kr:9000/oauth2/revoke").build();

	        revokeTokenWebClient
	                .post()
	                .body(BodyInserters.fromMultipartData(map))
	                .retrieve()
	                .bodyToMono(String.class)
	                .block();

	        return;
	    }	
	}
}
