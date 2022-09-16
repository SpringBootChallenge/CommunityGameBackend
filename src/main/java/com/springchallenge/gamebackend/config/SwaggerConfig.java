package com.springchallenge.gamebackend.config;

import jdk.jshell.SourceCodeAnalysis;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;
//
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket swaggerApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.springchallenge.gamebackend.controller"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo())
                ;
    }

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder().title("Game Community - REST API")
                .description("Spring Boot application for the gaming community")
                .contact(new Contact("Laura Garcia", "https://github.com/laura-gar",  "laura.garcia@endava.com"))
//                .contact(new Contact("Juan David Valencia", "https://github.com/juanvalag2019",  "juan.valencia@endava.com"))
                .version("1.0")
                .build();
    }


}
