package com.course.practicaljava.common;

import io.micrometer.core.ipc.http.HttpSender;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
public class Swagger2Config {

    private ApiInfo buildApiInfo(){
        Contact contact = new Contact("Your name", "http://www.yoursite.com", "your@email.com");

        return new ApiInfo("Practical Java API", "Practical Java API description", "1.0.0", "",
                contact, "", "", Collections.emptyList());
    }

    @Bean
    public Docket swaggerApi() {
        return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any()).apis(RequestHandlerSelectors.basePackage("com.course")).build()
                .apiInfo(buildApiInfo()).useDefaultResponseMessages(false);
    }

    @Bean
    public UiConfiguration tryItOutDisable() {
        final String[] methodsWithButtonTry = { "get" };

        return UiConfigurationBuilder.builder().supportedSubmitMethods(methodsWithButtonTry).build();
    }

}