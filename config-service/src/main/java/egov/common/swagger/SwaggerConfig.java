package egov.common.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import lombok.RequiredArgsConstructor;

@OpenAPIDefinition(
        info = @Info(title = "config service API 명세서",
                description = "파일럿을 위한 환경 자동 적용 샘플 서비스 API 명세서",
                version = "v1"))
@RequiredArgsConstructor
@Configuration
public class SwaggerConfig {
	
	@Bean
    OpenAPI openAPI() {
		return new OpenAPI()
        ;
    }
	 
}