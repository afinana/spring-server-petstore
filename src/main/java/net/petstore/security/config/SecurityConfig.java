package net.petstore.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // Disable JWT/Keycloak validation and allow anonymous access to all endpoints
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/v2/**", "/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**",
                                "/actuator/**")
                        .permitAll()
                        .anyRequest().permitAll());

        // No session management or authentication mechanism
        return http.build();
    }
}