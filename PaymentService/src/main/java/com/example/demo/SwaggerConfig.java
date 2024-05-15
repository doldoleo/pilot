package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.OAuthFlow;
import io.swagger.v3.oas.annotations.security.OAuthFlows;
import io.swagger.v3.oas.annotations.security.OAuthScope;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;
import lombok.RequiredArgsConstructor;

@OpenAPIDefinition(
        info = @Info(title = "지급결제 API 명세서",
                description = "파일럿을 위한 결제 샘플 서비스 API 명세서",
                version = "v1"))
@RequiredArgsConstructor
@Configuration
@SecurityScheme(
	name = "security_auth", 
	type = SecuritySchemeType.OAUTH2,
    flows = @OAuthFlows(clientCredentials  = 
                        @OAuthFlow(tokenUrl = "${openapi.oAuthFlow.tokenUrl}", 
                                   scopes = {@OAuthScope(name = "openid", description = "openid scope")})))
public class SwaggerConfig {
	@Bean
    OpenAPI openAPI() {
        return new OpenAPI()
                .addServersItem(new Server().url("/"));
    }

	
}