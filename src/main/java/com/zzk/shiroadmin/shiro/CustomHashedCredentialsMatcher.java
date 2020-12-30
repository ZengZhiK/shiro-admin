package com.zzk.shiroadmin.shiro;

import com.zzk.shiroadmin.common.exception.BusinessException;
import com.zzk.shiroadmin.common.exception.enums.BusinessExceptionType;
import com.zzk.shiroadmin.common.utils.JwtTokenUtils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;

/**
 * 自定义密码比较器
 *
 * @author zzk
 * @create 2020-12-23 16:11
 */
public class CustomHashedCredentialsMatcher extends HashedCredentialsMatcher {
    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        CustomUsernamePasswordToken customToken = (CustomUsernamePasswordToken) token;
        String accessToken = (String) customToken.getCredentials();

        //校验token
        if (!JwtTokenUtils.validateToken(accessToken)) {
            throw new BusinessException(BusinessExceptionType.TOKEN_PAST_DUE_ERROR);
        }
        return true;
    }
}
