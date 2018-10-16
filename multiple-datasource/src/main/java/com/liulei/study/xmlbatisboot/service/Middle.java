package com.liulei.study.xmlbatisboot.service;

import com.liulei.study.xmlbatisboot.configure.DS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Middle {
    @Autowired
    private PersonService personService;
   // @DS("dataSource2")
    public Long queryCount(){
        return personService.queryCount();
    }

}
