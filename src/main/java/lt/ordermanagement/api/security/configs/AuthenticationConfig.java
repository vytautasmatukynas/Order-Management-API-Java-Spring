package lt.ordermanagement.api.security.configs;

import lombok.RequiredArgsConstructor;
import lt.ordermanagement.api.security.repositories.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Configuration class responsible for setting up authentication-related components
 * in the Spring application. This includes defining a custom UserDetailsService,
 * configuring an AuthenticationProvider using DaoAuthenticationProvider,
 * creating an AuthenticationManager, and setting up a BCryptPasswordEncoder for password hashing.
 */
@Configuration
@RequiredArgsConstructor
public class AuthenticationConfig {

    private final UserRepository userRepository;

    /**
     * Defines a custom UserDetailsService implementation.
     *
     * @return The custom UserDetailsService implementation.
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> userRepository.findByUsername(username).orElseThrow();
    }

    /**
     * Defines a custom AuthenticationProvider using DaoAuthenticationProvider.
     *
     * @return The configured AuthenticationProvider.
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();

        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());

        return authenticationProvider;
    }

    /**
     * Defines an AuthenticationManager.
     *
     * @param authenticationConfiguration The authentication configuration.
     * @return The AuthenticationManager.
     * @throws Exception If an error occurs while getting the AuthenticationManager.
     */
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * Defines a BCryptPasswordEncoder bean for password hashing.
     *
     * @return The BCryptPasswordEncoder bean.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
