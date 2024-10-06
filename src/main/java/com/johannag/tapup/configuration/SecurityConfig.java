package com.johannag.tapup.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Configures the security filter chain for the application.
     * <p>
     * This method customizes the security settings for the application, including:
     * <ul>
     *     <li>Disabling Cross-Site Request Forgery (CSRF) protection.</li>
     *     <li>Establishing session management policy to be stateless.</li>
     *     <li>Configuring error handling for unauthorized requests.</li>
     *     <li>Setting permissions on various endpoints, allowing public access to specific paths.</li>
     *     <li>Ensuring that all other requests are authenticated.</li>
     *     <li>Adding an authorization filter.</li>
     * </ul>
     * </p>
     *
     * @param http the {@link HttpSecurity} object used to configure security settings for the application.
     * @return the built {@link SecurityFilterChain} that contains the security configuration.
     * @throws Exception if an error occurs while configuring the security filter chain.
     */

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // Disable CSRF
        http.csrf(AbstractHttpConfigurer::disable);

        // Establish session management
        http.sessionManagement(sessionManagement ->
                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // Configure Error handling for unauthorized request
        //http.exceptionHandling(
        //        errorHandling -> errorHandling.authenticationEntryPoint(customAuthenticationEntryPoint));

        // Set permissions on endpoints
        http.authorizeHttpRequests(authorize ->
                authorize.requestMatchers("/api/v1")
                        .permitAll()
                        .requestMatchers("/api/v1/healthcheck/**")
                        .permitAll()
                        .requestMatchers("/api/v1/version")
                        .permitAll()
                        .requestMatchers("/api-docs/**")
                        .permitAll()
                        .requestMatchers("/swagger-ui/**")
                        .permitAll()
                        .requestMatchers("/docs")
                        .permitAll()
                        .requestMatchers("/error/**")
                        .permitAll().anyRequest()
                        .authenticated()
        );

        // Add Authorization filter
        //http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
