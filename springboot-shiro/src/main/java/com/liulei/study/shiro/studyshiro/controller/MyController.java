package com.liulei.study.shiro.studyshiro.controller;

import com.liulei.study.shiro.studyshiro.pojo.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class MyController {

    @RequestMapping({"/login"})
    public String index(){
        return "login1";
    }

    @PostMapping("/login_in")
    public ModelAndView loginIn(@ModelAttribute User user) {
        String userCode = user.getUsercode();
        String userPwd = user.getPassword();
        // shiro认证
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(userCode, userPwd);
        String msg;
        ModelAndView mv=new ModelAndView("redirect:/index1.html");
        try {
            subject.login(token);
        } catch (UnknownAccountException e) {
            msg="账户不存在";
            mv.addObject("msg",msg);
            return mv;
        } catch (DisabledAccountException e) {

            msg="账户存在问题";
            mv.addObject("msg",msg);
            return mv;
        } catch (AuthenticationException e) {

            msg="密码错误";
            mv.addObject("msg",msg);
            return mv;
        } catch (Exception e) {
//            log.info("登陆异常", e);
            msg="登陆异常";
            mv.addObject("msg",msg);
            return mv;
        }
        String res = subject.getPrincipals().toString();
        if (subject.hasRole("admin")) {
            res = res + "----------你拥有admin权限";
        }
        if (subject.hasRole("guest")) {
            res = res + "----------你拥有guest权限";
        }
        msg="登陆成功";
        mv.addObject("msg",msg+res);

        return mv;
    }
    @RequestMapping("/403")
    public String unauthorizedRole(){
        System.out.println("------没有权限-------");
        return "403error";
    }
}
