package com.zzk.shiroadmin.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 系统配置
 *
 * @author zzk
 * @create 2021-02-19 16:21
 */
@Configuration
public class WebAppConfig implements WebMvcConfigurer {
    @Value("${file.static-path}")
    private String filStaticPath;

    @Value("${file.path}")
    private String filePath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(filStaticPath).addResourceLocations("file:" + filePath);
    }
}
