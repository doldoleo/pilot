package fivefinger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class ScgUIApplication {

	public static void main(String[] args) {
		SpringApplication.run(ScgUIApplication.class, args);
	}
	
	@Bean
	RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
		RestTemplate template = restTemplateBuilder.build();
		template.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
	    return template;
	}

}
