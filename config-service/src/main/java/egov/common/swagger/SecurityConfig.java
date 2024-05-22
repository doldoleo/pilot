package egov.common.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration(proxyBeanMethods = false)
public class SecurityConfig {

	/**
	 * 
	 * @return
	 */
	@Bean
	WebSecurityCustomizer webSecurityCustomizer() {
		return web -> web.debug(false).ignoring().requestMatchers(
				"/swagger-ui/**", 
				"/swagger-ui.html", 
				"/api-docs/user",
				"/webjars/**", "/swagger/**");
	}
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(AbstractHttpConfigurer::disable);
		http.cors(corsCustomizer -> corsCustomizer.configurationSource(new CorsConfig().corsConfigurationSource()));
		http.securityMatcher("/api/v1/user/greeting")
				.authorizeHttpRequests((authorize) -> authorize.anyRequest().authenticated())
				.oauth2ResourceServer((oauth2) -> oauth2.jwt(Customizer.withDefaults()));
		return http.build();
	}
	
	

}
