package egov.common.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
public class ResourceServerSecurityConfig {
	@Bean 
	 public SecurityWebFilterChain configureResourceServer(ServerHttpSecurity httpSecurity) throws Exception {
	  
	  return httpSecurity
	    .authorizeExchange().pathMatchers("/actuator/health/**").permitAll()     
	    .pathMatchers(HttpMethod.GET,"/webjars/**").permitAll()
	    .pathMatchers(HttpMethod.GET,"/swagger-ui/index.html").permitAll()    
	    .pathMatchers(HttpMethod.GET,"/swagger-resources/**").permitAll()
	    .pathMatchers(HttpMethod.GET,"/api/v1/user/api-docs/**").permitAll()
	    
	    .anyExchange().authenticated()
	    .and()
	     .oauth2ResourceServer().jwt().and()     
	    .and().build();
	 } 
}
