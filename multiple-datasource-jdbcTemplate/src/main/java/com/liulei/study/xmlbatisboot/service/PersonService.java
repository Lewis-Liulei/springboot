package com.liulei.study.xmlbatisboot.service;

import com.liulei.study.xmlbatisboot.config.DS;
import com.liulei.study.xmlbatisboot.domain.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PersonService {
    @Autowired
    @Qualifier("jdbcTemplate2")
    private JdbcTemplate jdbcTemplate;


    public List<Person> getPersonById(String id){
        String sql = "select * from person";
        return jdbcTemplate.queryForList(sql,Person.class);

    }

    public Long queryCount(){
        String sql = "select count(1)  from person";
                return jdbcTemplate.queryForObject(sql, Long.class);

    }
    //
    public int insertPerson(Person person){

        return 0;
    }
}
