package com.liulei.study.xmlbatisboot.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.liulei.study.xmlbatisboot.configure.DS;
import com.liulei.study.xmlbatisboot.configure.DataSourceHelper;
import com.liulei.study.xmlbatisboot.domain.Person;
import com.liulei.study.xmlbatisboot.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@EnableTransactionManagement
public class TestController {

    @Autowired
    private PersonService personService;
    @RequestMapping("/getPersonById")
    public <T> T getPersonById(@RequestParam String id){
        return (T)personService.getPersonById(id);
    }
    @RequestMapping("/insert")
    public int insertPerson(){
        Person person;
        for(int age=28;age<38;age++){
            person = new Person();
            person.setId("sdfdf");
            person.setName("adsfdf");
            person.setAddress("adsfdf");
            person.setAge(age);
            /*if(age<30)
                //这里做了切换数据源
                DataSourceHelper.setSqlSessionFactoryEnvironment("dataSource2");
            else
                //这里做了切换数据源
                DataSourceHelper.setSqlSessionFactoryEnvironment("dataSource1");*/
            personService.insertPerson(person);

        }
        return 0;
    }
    @RequestMapping("select")
    @DS("dataSource2")
    public PageInfo<Person> queryPerson(){

        return personService.queryPerson();
    }

    @RequestMapping("select2")
    public Page<Person> queryPerson2(){

        return personService.queryPerson2();
    }
}
