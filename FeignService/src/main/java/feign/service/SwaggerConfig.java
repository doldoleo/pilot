package feign.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;
import lombok.RequiredArgsConstructor;

@OpenAPIDefinition(
        info = @Info(title = "OpenFeign 서비스를 적용한 MERGE API 명세서",
                description = "파일럿을 위한 OpenFeign 샘플 서비스 API 명세서",
                version = "v1"))
@RequiredArgsConstructor
@Configuration
public class SwaggerConfig {
	@Bean
    OpenAPI openAPI() {
        return new OpenAPI()
                .addServersItem(new Server().url("/"));
    }
	 
}