package com.liulei.study.xmlbatisboot.service;

import com.liulei.study.xmlbatisboot.configure.DS;
import com.liulei.study.xmlbatisboot.dao.PersonMapper;
import com.liulei.study.xmlbatisboot.domain.Person;
import common.jdbc.datasource.DynamicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.sql.DataSource;

@Service
@Transactional
public class PersonService {
    @Autowired
    private PersonMapper personMapper;

    public Person getPersonById(String id){

       return personMapper.getPersonById(id);

    }
    @DS("dataSource2")
    public int insertPerson(Person person){

        return personMapper.insertPerson(person);
    }
}
