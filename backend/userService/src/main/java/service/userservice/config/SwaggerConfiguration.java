package service.userservice.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {

    @Value("${keycloak.auth-server-url}")
    String keycloakAuthUrl;

    String realm = "haushaltsapp";

    @Bean
    public OpenAPI envioronmentOpenApi(){
        String baseUrl = ""+keycloakAuthUrl+"/realms/"+realm+"/protocol/openid-connect";
        return new OpenAPI()
                .schemaRequirement(
                        "OAuth2 Authorization",
                        new SecurityScheme()
                                .type(SecurityScheme.Type.OAUTH2)
                                .flows(
                                        new OAuthFlows()
                                                .password(
                                                        new OAuthFlow()
                                                                .authorizationUrl(baseUrl+"/auth")
                                                                .tokenUrl(baseUrl+"/token")
                                                                .refreshUrl(baseUrl+"/token")
                                                                .scopes(new Scopes())
                                                )
                                )
                )
                .addSecurityItem(
                        new SecurityRequirement().addList("OAuth2 Authorization")
                );

    }
}
