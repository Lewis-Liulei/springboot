package com.liulei.study.xmlbatisboot.configure;

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
        return factoryBean.getObject();

    }
}
