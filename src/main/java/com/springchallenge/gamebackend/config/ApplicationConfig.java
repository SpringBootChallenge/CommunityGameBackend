package com.springchallenge.gamebackend.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(new Info()
                        .version("1.0")
                        .title("Game Community - REST API")
                        .description( "Spring Boot application for the gaming community"));
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
