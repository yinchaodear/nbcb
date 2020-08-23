package com.yuqiaotech.common.config;

import java.util.ArrayList;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig
{
    
    @Bean
    public Docket docker()
    {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo())
            .groupName("DEFAULT")
            .enable(true)
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.yuqiaotech"))
            .build();
    }
    
    private ApiInfo apiInfo()
    {
        return new ApiInfo("Yuqiao Admin Boot", "企业级快速开发平台", "1.0.0", "www.yuqiao.com",
            new Contact("", "www.yuqiao,com", ""), "apache", "", new ArrayList<>());
    }
}
