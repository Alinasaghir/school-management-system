package com.simplifi.school.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI schoolManagementOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("School Management API")
                        .description("Spring Boot application for School Management System")
                        .version("v1.0.0")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")));
    }
}