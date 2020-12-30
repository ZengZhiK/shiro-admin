package com.zzk.shiroadmin.shiro;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * 自定义令牌
 *
 * @author zzk
 * @create 2020-12-23 8:56
 */
public class CustomUsernamePasswordToken extends UsernamePasswordToken {
    private final String jwtToken;

    public CustomUsernamePasswordToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    @Override
    public Object getPrincipal() {
        return jwtToken;
    }

    @Override
    public Object getCredentials() {
        return jwtToken;
    }
}
