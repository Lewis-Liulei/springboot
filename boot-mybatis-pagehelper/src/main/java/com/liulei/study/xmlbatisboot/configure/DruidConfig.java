package com.liulei.study.xmlbatisboot.configure;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import common.jdbc.datasource.DynamicDataSource;
import common.jdbc.datasource.DynamicDataSourceEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DruidConfig {

    private static final Logger logger = LoggerFactory.getLogger(DruidConfig.class);

    public DruidConfig() {
    }
    @Autowired
    @Qualifier("dataSource1")
    private DataSource dataSource1;
    @Autowired
    @Qualifier("dataSource2")
    private DataSource dataSource2;
    @Bean
    public DynamicDataSourceEntry dynamicDataSourceEntry(){
        DynamicDataSourceEntry dynamicDataSourceEntry = new DynamicDataSourceEntry();
        return dynamicDataSourceEntry;
    }
    @Bean(name = "dynamicDataSource")
    public DynamicDataSource dynamicDataSource(){

        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        // 默认数据源
        dynamicDataSource.setDefaultTargetDataSource(dataSource1);
        dynamicDataSource.setDataSourceEntry(dynamicDataSourceEntry());
        // 配置多数据源
        Map<Object, Object> dsMap = new HashMap(5);
        dsMap.put("dataSource1", dataSource1);
        dsMap.put("dataSource2", dataSource2);

        dynamicDataSource.setTargetDataSources(dsMap);

        return dynamicDataSource;
    }

    @Bean
    public DataSourceTransactionManager transactionManager(DynamicDataSource dataSource) throws Exception {
                 return new DataSourceTransactionManager(dataSource);
             }

    @Bean
    public ServletRegistrationBean druidServlet() {
        logger.info("init Druid Servlet Configuration ");
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
// IP白名单
        servletRegistrationBean.addInitParameter("allow", "192.168.2.25,127.0.0.1");
// IP黑名单(共同存在时，deny优先于allow)
        servletRegistrationBean.addInitParameter("deny", "192.168.1.100");
//控制台管理用户
        servletRegistrationBean.addInitParameter("loginUsername", "admin");
        servletRegistrationBean.addInitParameter("loginPassword", "9527");
//是否能够重置数据 禁用HTML页面上的“Reset All”功能
        servletRegistrationBean.addInitParameter("resetEnable", "false");
        return servletRegistrationBean;
    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new WebStatFilter());
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        return filterRegistrationBean;
    }



}