package com.liulei.study.xmlbatisboot;

import com.liulei.study.xmlbatisboot.domain.Person;
import com.liulei.study.xmlbatisboot.common.jdbc.datasource.DynamicDataSource;
import com.liulei.study.xmlbatisboot.service.PersonService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
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
    @Test
    public void jdbcTemplateTest(){
        System.out.println("数据条数="+personService.queryCount()+"条");
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
                dataSource.getDataSourceEntry().set("dataSource2");

            }else{
                dataSource.getDataSourceEntry().set("dataSource1");

            }
            personService.insertPerson(person1);
        }


    }
    @Test
    public void  getxxx(){

        System.out.println(personService.getPersonById("12"));

    }
}
