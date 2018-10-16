package com.liulei.study.xmlbatisboot.dao;

import com.liulei.study.xmlbatisboot.configure.DS;
import com.liulei.study.xmlbatisboot.domain.Person;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface PersonMapper {
    public Person getPersonById(@Param("id") String id);
    public int insertPerson(Person person);
    @DS("dataSource2")
    public long queryCount();
}
