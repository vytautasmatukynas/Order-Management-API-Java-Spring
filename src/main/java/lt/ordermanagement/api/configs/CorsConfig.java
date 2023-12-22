package lt.ordermanagement.api.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuration class for Cross-Origin Resource Sharing (CORS) in a Spring application.
 * Enables the server to specify who can access its resources, typically used for allowing
 * requests from a different domain, especially relevant in the context of frontend and
 * backend applications running on separate servers.
 *
 * <p>
 * This class implements the WebMvcConfigurer interface, providing additional configuration
 * for Spring MVC, specifically for CORS.
 * </p>
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    /**
     * Configure Cross-Origin Resource Sharing (CORS) for the Spring application.
     *
     * @param registry The CorsRegistry used to register CORS configuration.
     *                 Allows specifying how CORS should be handled for different endpoints.
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowCredentials(true);
    }
}
