package lt.ordermanagement.api.security.configs;

import jakarta.servlet.Filter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Configuration class responsible for setting up security-related components
 * in the Spring application. This includes configuring a custom security filter chain
 * using JWT authentication, a custom UserDetailsService, an AuthenticationProvider
 * using DaoAuthenticationProvider, and a BCryptPasswordEncoder for password hashing.
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final Filter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    /**
     * ROLE_ADMIN have all the permissions of ROLE_MANAGER and ROLE_USER, and users with ROLE_MANAGER
     * have all the permissions of ROLE_USER.
     *
     * @return The configured RoleHierarchy.
     */
    @Bean
    public RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        roleHierarchy.setHierarchy("ROLE_ADMIN > ROLE_MANAGER > ROLE_USER");
        return roleHierarchy;
    }

    /**
     * Configures the security filter chain for the application.
     *
     * @param httpSecurity The HttpSecurity object to configure.
     * @return The configured SecurityFilterChain.
     * @throws Exception If an error occurs during configuration.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/v1/user/authenticate",
                                        "/v3/api-docs/**",
                                        "/swagger-ui/**").permitAll()

                        .requestMatchers("/api/v1/user/register",
                                        "/api/v1/user/delete",
                                        "/api/v1/users").hasRole("ADMIN")

                        .requestMatchers("/api/v1/add/order",
                                        "/api/v1/update/order/{orderId}",
                                        "/api/v1/delete/order/{orderId}",
                                        "/api/v1/order/{orderId}/add/item",
                                        "/api/v1/order/update/item/{itemId}",
                                        "/api/v1/order/delete/item/{itemId}").hasRole("MANAGER")

                        .requestMatchers("/api/v1/orders",
                                        "/api/v1/order/{orderId}",
                                        "/api/v1/order/search/{orderParam}",
                                        "/api/v1/order/{orderId}/items",
                                        "/api/v1/order/item/{itemId}",
                                        "/order/{orderId}/items/{itemName}",
                                        "/api/v1/user/change/password").hasRole("USER")
                        .anyRequest()
                        .authenticated())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }

}