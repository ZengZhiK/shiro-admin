package com.zzk.shiroadmin.common.utils;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

/**
 * JWT配置信息
 *
 * @author zzk
 * @create 2020-12-22 13:59
 */
@Configuration
@ConfigurationProperties(prefix = "jwt")
@Data
public class JwtTokenConfig {
    private String secretKey;
    private Duration accessTokenExpireTime;
    private Duration refreshTokenExpireTime;
    private Duration refreshTokenExpireAppTime;
    private String issuer;
}
