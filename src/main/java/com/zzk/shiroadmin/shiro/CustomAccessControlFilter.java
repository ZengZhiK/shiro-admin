package com.zzk.shiroadmin.shiro;

import com.zzk.shiroadmin.common.constant.ShiroConstants;
import com.zzk.shiroadmin.common.exception.BusinessException;
import com.zzk.shiroadmin.common.exception.enums.BusinessExceptionType;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.springframework.util.StringUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * 自定义Shiro过滤器
 *
 * @author zzk
 * @create 2020-12-23 9:02
 */
@Slf4j
public class CustomAccessControlFilter extends AccessControlFilter {
    /**
     * 如果返回的是true 就流转到下一个链式调用
     * 返回false 就会流转到 onAccessDenied方法
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        return false;
    }

    /**
     * 如果返true 就会流转到下一个链式调用
     * false 就是不会流转到下一个链式调用
     */
    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        log.info("Shiro拦截      : {}", request.getRequestURL());

        String accessToken = request.getHeader(ShiroConstants.ACCESS_TOKEN);

        try {
            if (StringUtils.isEmpty(accessToken)) {
                throw new BusinessException(BusinessExceptionType.TOKEN_IS_NULL_ERROR);
            }
            CustomUsernamePasswordToken customToken = new CustomUsernamePasswordToken(accessToken);
            getSubject(servletRequest, servletResponse).login(customToken);
        } catch (Exception e) {
            // 异常捕获，发送到ErrorController的/sys-error/filter-error
            request.setAttribute("filter.error", e);
            request.getRequestDispatcher("/sys-error/filter-error").forward(servletRequest, servletResponse);
            return false;
        }
        return true;
    }
}
