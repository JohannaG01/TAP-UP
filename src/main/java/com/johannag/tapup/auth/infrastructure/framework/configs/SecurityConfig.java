package com.johannag.tapup.auth.infrastructure.framework.configs;

import com.johannag.tapup.auth.infrastructure.framework.filters.AuthenticationFilter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {

    private final AuthenticationEntryPoint authenticationEntryPoint;
    private final AuthenticationFilter authenticationFilter;

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
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(exception -> exception.authenticationEntryPoint(authenticationEntryPoint))
                .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(authorize ->
                        authorize.requestMatchers(getAllPermittedRequests())
                                .permitAll()
                                .anyRequest()
                                .authenticated())
                .build();

    }

    private RequestMatcher[] getAllPermittedRequests() {
        return new RequestMatcher[]{
                new AntPathRequestMatcher("/api/v1"),
                new AntPathRequestMatcher("/api/v1/healthcheck/**"),
                new AntPathRequestMatcher("/api/v1/version"),
                new AntPathRequestMatcher("/api-docs/**"),
                new AntPathRequestMatcher("/swagger-ui/**"),
                new AntPathRequestMatcher("/docs"),
                new AntPathRequestMatcher("/error/**"),
                new AntPathRequestMatcher("/api/v1/users"),
                new AntPathRequestMatcher("/api/v1/login")
        };
    }
}
