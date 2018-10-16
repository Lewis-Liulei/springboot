package com.liulei.study.xmlbatisboot.configure;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.github.pagehelper.PageInterceptor;
import com.liulei.study.xmlbatisboot.common.jdbc.datasource.DynamicDataSource;
import com.liulei.study.xmlbatisboot.common.jdbc.datasource.DynamicDataSourceEntry;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Configuration
@MapperScan("com.liulei.study.xmlbatisboot.dao")
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


    @Value("${mybatis.mapper-locations}")
    private Resource[] mapperLocations;
    @Value("${mybatis.type-aliases-package}")
    private String typeAliasesPackage;

    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(DynamicDataSource dynamicDataSource) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dynamicDataSource); //
        factoryBean.setMapperLocations(mapperLocations);
        factoryBean.setTypeAliasesPackage(typeAliasesPackage);

        Interceptor[] interceptors= new Interceptor[1];
        interceptors[0]=getPageInterceptor();
        factoryBean.setPlugins(interceptors);
        return factoryBean.getObject();

    }

    @Bean
    public PageInterceptor getPageInterceptor(){
        PageInterceptor pageInterceptor = new PageInterceptor();
        Properties properties = new Properties();
        properties.put("helperDialect","mysql");
        pageInterceptor.setProperties(properties);
        return pageInterceptor;
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