package com.widetech.menuapp.config.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authz -> authz
                .requestMatchers("/menu/**").permitAll()
                .requestMatchers("/menu/item/**").permitAll()
                .requestMatchers("swagger-ui.html","/api/**","/v2/","/v2/api-docs-ext","/cart/**","/order/**").permitAll()
                .anyRequest().authenticated());
        return http.build();
    }
}

