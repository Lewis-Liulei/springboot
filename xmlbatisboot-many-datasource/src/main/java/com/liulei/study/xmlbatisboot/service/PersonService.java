package com.liulei.study.xmlbatisboot.service;

import com.liulei.study.xmlbatisboot.configure.DS;
import com.liulei.study.xmlbatisboot.dao.PersonMapper;
import com.liulei.study.xmlbatisboot.domain.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PersonService {
    @Autowired
    private PersonMapper personMapper;

    public Person getPersonById(String id){

       return personMapper.getPersonById(id);

    }

    public int insertPerson(Person person){

        return personMapper.insertPerson(person);
    }
    @DS("dataSource2")
    public Long queryCount(){
        return personMapper.queryCount();

    }


}
