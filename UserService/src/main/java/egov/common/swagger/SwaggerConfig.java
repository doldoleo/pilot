package egov.common.swagger;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.media.MapSchema;
import io.swagger.v3.oas.models.media.NumberSchema;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.security.OAuthFlow;
import io.swagger.v3.oas.models.security.OAuthFlows;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.RequiredArgsConstructor;

@OpenAPIDefinition(
        info = @Info(title = "User-Service API 명세서",
                description = "파일럿을 위한 사용자 샘플 서비스 API 명세서",
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
                                                                                             .tokenUrl(tokenUri))))
					   
					            .addSchemas("MyCustomSchema", new MapSchema()
					                   .addProperty("propA", new NumberSchema().example("1"))
					                   .addProperty("propB", new NumberSchema().example("2"))
					                   .addProperty("propC", new StringSchema().example("222")))
					            
//                            .addSchemas("ParameterMap", new Schema<Map<String, String>>().addProperty("sourceAccountId", 
//                               new StringSchema().example("1")).addProperty("targetAccountId", new StringSchema().example("2")))

					 )
                    .security(Arrays.asList(
                          new SecurityRequirement().addList("spring_oauth")))
            
        ;
    }
	 
}