package com.widetech.menuapp.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Author: athen
 * Date: 2/4/2024
 * Description: configuration of Swagger API doc
 */

@Configuration
public class Knife4jConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API of Menu order app")
                        .version("1.0")
                        .description("API for the menu app for a restaurant")
                        .contact(new Contact().name("Dian").email("athenting@outlook.com"))
                );
    }
}
