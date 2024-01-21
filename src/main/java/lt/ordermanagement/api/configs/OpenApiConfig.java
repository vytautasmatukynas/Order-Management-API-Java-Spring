package lt.ordermanagement.api.configs;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.SpringDocUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for customizing OpenAPI documentation using Springdoc.
 */
@Configuration
public class OpenApiConfig {

    /**
     * Custom OpenAPI bean to configure Swagger documentation.
     *
     * @return The configured OpenAPI object.
     */
    @Bean
    public OpenAPI customOpenAPI() {
        // Disable Springdoc annotations for ConfigurationProperties to avoid conflicts.
        SpringDocUtils.getConfig().addAnnotationsToIgnore(
                org.springframework.boot.context.properties.ConfigurationProperties.class);

        return new OpenAPI()
                // Configure security schemes for the API.
                .components(new Components()
                        .addSecuritySchemes("bearer-key", createSecurityScheme()))
                // Add a security requirement to include the "bearer-key" in API requests.
                .addSecurityItem(new SecurityRequirement().addList("bearer-key"))
                .info(new Info()
                        .title("Order Management REST API")
                        .version("1.0")
                        .description("Back-end REST API for Order Management App. " +
                                "Test CRUD operations with users: \"manager123\" \"pass123456\" / " +
                                "\"user123\" \"pass123456\"."));
    }

    /**
     * Create a SecurityScheme for JWT-based authentication.
     *
     * @return The configured SecurityScheme object.
     */
    private SecurityScheme createSecurityScheme() {
        return new SecurityScheme()
                // Set the type of security scheme to HTTP.
                .type(SecurityScheme.Type.HTTP)
                // Specify the authentication scheme as "bearer" for JWT.
                .scheme("bearer")
                // Define the bearer format as "JWT".
                .bearerFormat("JWT")
                // Specify that the token should be included in the header.
                .in(SecurityScheme.In.HEADER)
                // Define the name of the header carrying the JWT token as "Authorization".
                .name("Authorization");
    }

}

