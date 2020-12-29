package com.zzk.shiroadmin.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zzk
 * @create 2020-12-21 10:20
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Value("${swagger.enable}")
    private boolean enable;

    @Bean
    public Docket createDocket() {
        List<Parameter> params = new ArrayList<>();
        ParameterBuilder accessTokenBuilder = new ParameterBuilder();
        ParameterBuilder refreshTokenBuilder = new ParameterBuilder();
        accessTokenBuilder.name("accessToken").description("令牌")
                .modelRef(new ModelRef("String")).parameterType("header").required(false);
        refreshTokenBuilder.name("refreshToken").description("刷新令牌")
                .modelRef(new ModelRef("String")).parameterType("header").required(false);
        params.add(accessTokenBuilder.build());
        params.add(refreshTokenBuilder.build());

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.zzk.shiroadmin.controller"))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(params)
                .enable(enable);
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("基于Shiro的后台权限管理系统")
                .description("后台权限管理系统接口文档")
                .termsOfServiceUrl("")
                .version("1.0")
                .build();
    }
}
