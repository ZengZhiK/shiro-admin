package com.zzk.shiroadmin.config;

import com.zzk.shiroadmin.shiro.CustomAccessControlFilter;
import com.zzk.shiroadmin.shiro.CustomHashedCredentialsMatcher;
import com.zzk.shiroadmin.shiro.CustomRealm;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Shiro配置
 *
 * @author zzk
 * @create 2020-12-23 16:37
 */
@Configuration
public class ShiroConfig {
    @Bean
    public CustomHashedCredentialsMatcher customHashedCredentialsMatcher() {
        return new CustomHashedCredentialsMatcher();
    }

    @Bean
    public CustomRealm customRealm(CustomHashedCredentialsMatcher customHashedCredentialsMatcher) {
        CustomRealm customRealm = new CustomRealm();
        customRealm.setCredentialsMatcher(customHashedCredentialsMatcher);
        return customRealm;
    }

    @Bean
    public SecurityManager securityManager(CustomRealm customRealm) {
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        defaultWebSecurityManager.setRealm(customRealm);
        return defaultWebSecurityManager;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        Map<String, Filter> linkedHashMap = new LinkedHashMap<>();
        linkedHashMap.put("token", new CustomAccessControlFilter());
        shiroFilterFactoryBean.setFilters(linkedHashMap);

        LinkedHashMap<String, String> hashMap = new LinkedHashMap<>();
        hashMap.put("/api/user/login", "anon");
        // 开放swagger-ui地址
        hashMap.put("/swagger/**", "anon");
        hashMap.put("/v2/api-docs", "anon");
        hashMap.put("/swagger-ui.html", "anon");
        hashMap.put("/swagger-resources/**", "anon");
        hashMap.put("/csrf", "anon");
        hashMap.put("/", "anon");
        // 静态资源
        hashMap.put("/webjars/**", "anon");
        hashMap.put("/favicon.ico", "anon");
//        hashMap.put("/captcha.jpg", "anon");
        // druid sql监控配置
        hashMap.put("/druid/**", "anon");

        hashMap.put("/**", "token,authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(hashMap);
        return shiroFilterFactoryBean;
    }

    /**
     * 开启shiro aop注解支持.
     * 使用代理方式;所以需要开启代码支持;
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }
}
