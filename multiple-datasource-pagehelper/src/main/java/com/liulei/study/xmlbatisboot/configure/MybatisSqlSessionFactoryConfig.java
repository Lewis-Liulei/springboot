package com.liulei.study.xmlbatisboot.configure;

import com.github.pagehelper.PageInterceptor;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@MapperScan("com.liulei.study.xmlbatisboot.dao")
public class MybatisSqlSessionFactoryConfig {
    @Autowired
    @Qualifier("dataSource1")
    private DataSource dataSource1;

    @Value("${mybatis.mapper-locations}")
    private Resource[] mapperLocations;
    @Value("${mybatis.type-aliases-package}")
    private String typeAliasesPackage;

    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource1); //
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

}
