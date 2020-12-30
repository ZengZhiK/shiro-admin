package com.zzk.shiroadmin.shiro;

import com.zzk.shiroadmin.common.constant.JwtConstants;
import com.zzk.shiroadmin.common.utils.JwtTokenUtils;
import io.jsonwebtoken.Claims;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.Collection;

/**
 * 自定义Realm
 *
 * @author zzk
 * @create 2020-12-23 9:44
 */
public class CustomRealm extends AuthorizingRealm {
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof CustomUsernamePasswordToken;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String accessToken = (String) principals.getPrimaryPrincipal();
        Claims claims = JwtTokenUtils.getClaimsFromToken(accessToken);
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();

        if (claims.get(JwtConstants.JWT_ROLES_INFO) != null) {
            authorizationInfo.addRoles((Collection<String>) claims.get(JwtConstants.JWT_ROLES_INFO));
        }

        if (claims.get(JwtConstants.JWT_PERMISSIONS_INFO) != null) {
            authorizationInfo.addStringPermissions((Collection<String>) claims.get(JwtConstants.JWT_PERMISSIONS_INFO));
        }

        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) {
        CustomUsernamePasswordToken customToken = (CustomUsernamePasswordToken) token;
        return new SimpleAuthenticationInfo(customToken.getPrincipal(), customToken.getCredentials(), this.getName());
    }
}
