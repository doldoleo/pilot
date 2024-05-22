package egov.common.swagger;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

	 @Bean
	    CorsFilter corsFilter() {
		 	CorsConfigurationSource source = corsConfigurationSource();
	        return new CorsFilter(source);
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