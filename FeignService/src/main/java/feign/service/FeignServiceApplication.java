package feign.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({ "feign.service", "openfeign" })
public class FeignServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FeignServiceApplication.class, args);
	}

}
