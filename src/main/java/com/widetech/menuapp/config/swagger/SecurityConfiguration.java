package com.widetech.menuapp.config.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
//@Profile(value = "dev")
public class SecurityConfiguration {
    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.authorizeRequests(authz -> authz
                        .requestMatchers("/swagger-ui.html", "/v2/api-docs", "/swagger-resources/**", "/configuration/**", "/webjars/**").permitAll()
                        .requestMatchers("/api/**", "/v2/", "/v2/api-docs-ext").permitAll())
                .csrf(AbstractHttpConfigurer::disable); //shut down csrf protection for dev env

        return http.build();
    }

//    @Autowired
//    public  void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder())
//                .withUser("user1")
//                .password(new BCryptPasswordEncoder().encode("123456"))
//                .roles("ADMIN","USER")
//                .and()
//                .withUser("user2")
//                .password(new BCryptPasswordEncoder().encode("123456"))
//                .roles("USER");
//    }
}

