package com.leo.checkin.config;

import com.leo.checkin.autoconfigure.SwaggerProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Description:
 * @Author: leo
 * @Date: 2021/9/19 0:03
 */

@Configuration
@EnableSwagger2
public class Knife4jConfig {

    @Autowired
    private SwaggerProperties swaggerProperties;

    public Knife4jConfig() {
    }

    @Bean
    public Docket defaultApi2() {
        return (new Docket(DocumentationType.SWAGGER_2))
                .apiInfo((new ApiInfoBuilder())
                        .title(this.swaggerProperties.getTitle())
                        .description(this.swaggerProperties.getDescription())
                        .version(this.swaggerProperties.getVersion())
                        .build())
                .groupName(this.swaggerProperties.getGroupName())
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }

}

