package com.liulei.study.shiro.studyshiro.config;

import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.DelegatingFilterProxy;

import javax.servlet.DispatcherType;

@Configuration
@ConfigurationProperties(prefix = "shiro")
public class Config_Shiro {
   // @Value("${shiro.filterChainDefinitions}")
    private String filterChainDefinitions;

    public String getFilterChainDefinitions() {
        return filterChainDefinitions;
    }

    public void setFilterChainDefinitions(String filterChainDefinitions) {
        this.filterChainDefinitions = filterChainDefinitions;
    }

    @Bean(name="realm1")

    public Realm getRealm(){
        return  new MyRealm1();

    }
    @Bean(name="securityManager")
    public SecurityManager getSecurityManager(Realm realm1){
        return new DefaultWebSecurityManager(realm1);
    }
    @Bean(name="lifecycleBeanPostProcessor")
    public static LifecycleBeanPostProcessor getLifecycleBeanPostProcessor(){
        return  new LifecycleBeanPostProcessor();
    }

    @Bean(name="shiroFilter")
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(SecurityManager securityManager){
        ShiroFilterFactoryBean factoryBean=new ShiroFilterFactoryBean();
        factoryBean.setSecurityManager(securityManager);
        factoryBean.setFilterChainDefinitions(filterChainDefinitions);
        factoryBean.setLoginUrl("/login");
        factoryBean.setSuccessUrl("/index1");
        factoryBean.setUnauthorizedUrl("/403");
        return factoryBean;
    }

    @Bean
    public FilterRegistrationBean myFilterRegistration() {
        //filter
        DelegatingFilterProxy filterProxy= new DelegatingFilterProxy();
        filterProxy.setBeanName("shiroFilter1");
        filterProxy.setTargetBeanName("shiroFilter");
        filterProxy.setTargetFilterLifecycle(true);
        FilterRegistrationBean registration = new FilterRegistrationBean();
        //registration.setInitParameters();
        registration.setFilter(filterProxy);
        registration.setName("myShiroFilter");
        registration.addUrlPatterns("/*");
        registration.setDispatcherTypes(DispatcherType.REQUEST);
        return registration;
    }


}
