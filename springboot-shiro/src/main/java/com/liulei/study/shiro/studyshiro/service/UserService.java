package com.liulei.study.shiro.studyshiro.service;

import com.liulei.study.shiro.studyshiro.pojo.User;
import com.liulei.study.shiro.studyshiro.pojo.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<UserRole> queryUserRolesByUsercode(String usercode){
        String sql ="select * from userrole where usercode=?";
        return jdbcTemplate.query(sql,new BeanPropertyRowMapper(UserRole.class),usercode);
    }

    public List<User> queryUsersByUsercode(String usercode){
        String sql ="select * from user where usercode=?";
        return jdbcTemplate.query(sql,new BeanPropertyRowMapper(User.class),usercode);
    }

}
