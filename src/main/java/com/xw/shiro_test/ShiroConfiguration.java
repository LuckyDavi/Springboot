package com.xw.shiro_test;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * shiro的配置类
 */
@Configuration
public class ShiroConfiguration {

    /*************************** 配置shiro ***********************/
    @Bean("shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(@Qualifier("securityManager") SecurityManager securityManager){
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        bean.setSecurityManager(securityManager);

        //注入自定义的logout过滤器
        Map<String, Filter> filters = bean.getFilters();
        filters.put("logout",new ShiroLogoutFilter());

        //定义登录的url
        bean.setLoginUrl("/login.html");
        //定义登录成功后跳转的url
        bean.setSuccessUrl("/index");
        //定义没有权限访问的时候跳转的url
        bean.setUnauthorizedUrl("/unauthorized");

        //key是一个表达式，代表访问的请求，value代表使用什么样的拦截器
        LinkedHashMap<String,String> filterChainDefinitionMap = new LinkedHashMap<>();
        filterChainDefinitionMap.put("/index","authc");
        filterChainDefinitionMap.put("/login.html","anon");
        filterChainDefinitionMap.put("/","anon");
        filterChainDefinitionMap.put("/logout","logout");
        filterChainDefinitionMap.put("/**","anon");
        bean.setFilterChainDefinitionMap(filterChainDefinitionMap);

        return bean;
    }

    @Bean("securityManager")
    public SecurityManager securityManager(@Qualifier("authRealm") AuthRealm authRealm){
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        manager.setRealm(authRealm);
        return manager;
    }

    @Bean("authRealm")
    public AuthRealm authRealm(@Qualifier("credentiaMatcher") CredentiaMatcher matcher){
        AuthRealm authRealm = new AuthRealm();
        authRealm.setCredentialsMatcher(matcher);
        return  authRealm;
    }

    //使用自定义的校验规则
    @Bean("credentiaMatcher")
    public CredentiaMatcher credentiaMatcher(){
        return  new CredentiaMatcher();
    }

    /**********  配置shiro和spring的关联 ***************/
    //这样就可使用自定义的securityManager
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(@Qualifier("securityManager") SecurityManager securityManager){
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }

    /** 开启spring注解支持@RequireRoles、@RequirePermissions **/
    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
        creator.setProxyTargetClass(true);
        return creator;
    }

}
