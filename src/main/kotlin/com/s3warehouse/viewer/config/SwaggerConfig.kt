/*
 * Copyright (c) 2020 Alex Antukhov
 */

package com.s3warehouse.viewer.config

import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2

@Component
@EnableSwagger2
class SwaggerConfig {

    @org.springframework.beans.factory.annotation.Value("\${swagger.enabled:false}")
    var enableSwagger : Boolean = false

    @Bean
    fun createRestApi(): Docket {
        return Docket(DocumentationType.SWAGGER_2)
            .enable(enableSwagger)
            .apiInfo(apiInfo())
            .select()
            .apis(RequestHandlerSelectors.any())
            .paths(PathSelectors.regex("/s3.*").or(PathSelectors.regex("/health")))
            .build()
    }

    private fun apiInfo(): ApiInfo {
        return ApiInfoBuilder()
            .title("One stop guide: Kotlin + Spring Boot 2 + AWS S3 + Testcontainers")
            .version("0.1")
            .description("Created by <a href=\"https://github.com/antukhov\">Alex Antukhov</a>")
            .build()
    }
}