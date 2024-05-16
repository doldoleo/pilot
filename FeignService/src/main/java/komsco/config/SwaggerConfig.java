package komsco.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.OAuthFlow;
import io.swagger.v3.oas.models.security.OAuthFlows;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.RequiredArgsConstructor;

@OpenAPIDefinition(
        info = @Info(title = "OpenFeign 서비스를 적용한 MERGE API 명세서",
                description = "파일럿을 위한 OpenFeign 샘플 서비스 API 명세서",
                version = "v1"))
@RequiredArgsConstructor
@Configuration
public class SwaggerConfig {
	@Value("${spring.security.oauth2.client.provider.komsco.token-uri}")
	String tokenUri;
	
	@Bean
    OpenAPI openAPI() {
		return new OpenAPI()
			    .components(
				   new Components().addSecuritySchemes("spring_oauth", 
						                                new SecurityScheme().type(SecurityScheme.Type.OAUTH2)
                                                                            .description("Oauth2 flow")
                                                                            .flows(new OAuthFlows()
                                                                                         .clientCredentials(new OAuthFlow()
                                                                                         .tokenUrl(tokenUri)))
                ))
                .security(Arrays.asList(
                      new SecurityRequirement().addList("spring_oauth")))
    ;
    }
	 
}