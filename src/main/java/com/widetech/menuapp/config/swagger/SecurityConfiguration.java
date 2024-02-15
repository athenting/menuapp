package com.widetech.menuapp.config.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SecurityConfiguration {
    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.authorizeRequests(authz -> authz
                .requestMatchers("/swagger-ui.html", "/v2/api-docs", "/swagger-resources/**", "/configuration/**", "/webjars/**").permitAll()
                .requestMatchers("/menu/**").permitAll()
                .requestMatchers("/menu/item/**").permitAll()
                .requestMatchers("/api/**", "/v2/", "/v2/api-docs-ext", "/cart/**", "/order/**","api/admin").permitAll());

        return http.build();
    }


//    protected void configure(HttpSecurity http) throws Exception {
//        http.formLogin(Customizer.withDefaults()).authorizeRequests()
//                .requestMatchers("/", "/swagger-ui.html", "/login.html").permitAll() // 设置哪些路径可以直接访问，不需要认证
//                .anyRequest().authenticated()
//                .and().csrf(Customizer.withDefaults()); // 关闭csrf防护
//    }

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

