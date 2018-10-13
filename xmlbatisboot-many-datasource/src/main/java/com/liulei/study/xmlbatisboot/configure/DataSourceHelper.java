package com.liulei.study.xmlbatisboot.configure;

import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.transaction.SpringManagedTransactionFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Component
public class DataSourceHelper implements InitializingBean,ApplicationContextAware {

    private ApplicationContext applicationContext;
    private static Map<String,Environment> Environments;
    private static Configuration configuration;
    private SqlSessionFactory sqlSessionFactory;
    @Override
    public void afterPropertiesSet() throws Exception {

        //获取所有数据源
        Map<String, DataSource> dataSources = applicationContext.getBeansOfType(DataSource.class);
        sqlSessionFactory = applicationContext.getBean(SqlSessionFactory.class);
        //获取sqlSessionFactory的configuration
        configuration = sqlSessionFactory.getConfiguration();
        Environment environment;
        if(Environments==null){
            Environments=new HashMap<String,Environment>(dataSources.size());
        }
        for (Map.Entry<String, DataSource> entry : dataSources.entrySet()) {
            environment=new Environment(SqlSessionFactoryBean.class.getSimpleName(),
                    new SpringManagedTransactionFactory(),entry.getValue());
            //初始化所有数据源的environment，便于之后切换数据源使用
            Environments.put(entry.getKey(),environment);
        }
    }
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext=applicationContext;
    }

    public static void setSqlSessionFactoryEnvironment(String dsName){
        //切换Mybatis的Environment
        configuration.setEnvironment(Environments.get(dsName));
    }

}
