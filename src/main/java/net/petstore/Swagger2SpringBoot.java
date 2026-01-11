package net.petstore;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;


@EnableCaching
@SpringBootApplication
@EnableWebSecurity
@EnableMethodSecurity(jsr250Enabled = true) // Enable @RolesAllowed annotation
public class Swagger2SpringBoot{

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/public").permitAll()
                                .requestMatchers("/v2/**").authenticated()
                                .anyRequest().denyAll()
                )
                .oauth2ResourceServer(oauth2ResourceServer -> oauth2ResourceServer.jwt());
        return http.build();
    }

    public static void main(String[] args) {

        SpringApplication.run(Swagger2SpringBoot.class, args);
    }
}
