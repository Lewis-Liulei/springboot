package com.liulei.study.xmlbatisboot;

import com.liulei.study.xmlbatisboot.domain.Person;
import com.liulei.study.xmlbatisboot.service.PersonService;
import common.jdbc.datasource.DynamicDataSource;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.transaction.SpringManagedTransactionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;
import javax.sql.DataSource;

@RunWith(SpringRunner.class)
@SpringBootTest
@EnableTransactionManagement
public class XmlbatisbootApplicationTests {

    @Autowired
    private PersonService personService;

    private DynamicDataSource dataSource;
    @Resource(name="dynamicDataSource")
    public void setDataSource(DataSource dataSource) {
        this.dataSource = (DynamicDataSource)dataSource;
        //this.setDataSourceReadOnly(dataSource);
        //this.setDataSourceWrite(dataSource);
    }
    @Autowired
    @Qualifier("dataSource")
    private DataSource ds1;
    @Autowired
    @Qualifier("dataSource2")
    private DataSource ds2;
    private Environment e1;
    private Environment e2;
    private Configuration configuration;
    @Autowired
    private SqlSessionFactory sqlSessionFactory;
    @Test
    public void contextLoads() {
    }
    private void setSqlSessionFactoryEnvironment(int dsi){

        configuration=(configuration==null)?sqlSessionFactory.getConfiguration():configuration;
        e1=(e1==null)?configuration.getEnvironment():e1;
        e2=(e2==null)?new Environment(SqlSessionFactoryBean.class.getSimpleName(),
                new SpringManagedTransactionFactory(),
                ds2):e2;

        if(dsi==2){
            configuration.setEnvironment(e2);
        } else {
            configuration.setEnvironment(e1);

        }
    }

    @Test
    public void insertTest(){

        for(int age=28;age<33;age++){
            Person person1= new Person();
            person1.setId("sfdfd");
            person1.setName("sdfdff");
            person1.setAddress("dfsdfdf");
            person1.setAge(age);
            if(person1.getAge()>30){
                dataSource.getDataSourceEntry().set("ds2");
                setSqlSessionFactoryEnvironment(2);
            }else{
                dataSource.getDataSourceEntry().set("ds");
                setSqlSessionFactoryEnvironment(1);
            }
            personService.insertPerson(person1);
        }


    }
    @Test
    public void  getxxx(){

        System.out.println(personService.getPersonById("12"));

    }
}
