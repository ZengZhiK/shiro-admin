package com.zzk.shiroadmin.shiro;

import com.zzk.shiroadmin.common.constant.JwtConstants;
import com.zzk.shiroadmin.common.constant.RedisConstant;
import com.zzk.shiroadmin.common.exception.BusinessException;
import com.zzk.shiroadmin.common.exception.enums.BusinessExceptionType;
import com.zzk.shiroadmin.common.utils.JwtTokenUtils;
import com.zzk.shiroadmin.common.utils.RedisUtils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.TimeUnit;

/**
 * 自定义密码比较器
 *
 * @author zzk
 * @create 2020-12-23 16:11
 */
public class CustomHashedCredentialsMatcher extends HashedCredentialsMatcher {
    @Autowired
    private RedisUtils redisUtils;

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        CustomUsernamePasswordToken customToken = (CustomUsernamePasswordToken) token;
        String accessToken = (String) customToken.getCredentials();
        String userId = JwtTokenUtils.getUserId(accessToken);

        //判断是否被锁定
        if (redisUtils.hasKey(RedisConstant.ACCOUNT_LOCK_KEY + userId)) {
            throw new BusinessException(BusinessExceptionType.ACCOUNT_LOCKED_ERROR);
        }

        // 校验token
        if (!JwtTokenUtils.validateToken(accessToken)) {
            throw new BusinessException(BusinessExceptionType.TOKEN_INVALID_ERROR);
        }

        if (redisUtils.hasKey(JwtConstants.JWT_REFRESH_KEY + userId)) {
            // 判断用户是否已经刷新过
            if (redisUtils.getExpire(JwtConstants.JWT_REFRESH_KEY + userId, TimeUnit.MILLISECONDS) > JwtTokenUtils.getRemainingTime(accessToken)) {
                throw new BusinessException(BusinessExceptionType.TOKEN_INVALID_ERROR);
            }
        }


        return true;
    }
}
