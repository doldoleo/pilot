package egov.common.swagger;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.RequiredArgsConstructor;

@OpenAPIDefinition(
        info = @Info(title = "User-Service API 명세서",
                description = "파일럿을 위한 사용자 샘플 서비스 API 명세서",
                version = "v1"))
@RequiredArgsConstructor
@Configuration
public class SwaggerConfig {
	 @Bean
	 public GroupedOpenApi getItemApi() { 
		// "/user-service/**" 경로에 매칭되는 API를 'user'으로 그룹화하여 문서화한다. http://localhost/user-service/api-docs/user 으로 API 목록을 가져올수 있다
	        return GroupedOpenApi
	            .builder()
	            .group("회원서비스")
	            .pathsToMatch("/user-service/**")
	            .build();

	}
	 
}