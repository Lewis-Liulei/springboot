package com.liulei.study.shiro.studyshiro.config;

import com.liulei.study.shiro.studyshiro.pojo.User;
import com.liulei.study.shiro.studyshiro.pojo.UserRole;
import com.liulei.study.shiro.studyshiro.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class MyRealm1 extends AuthorizingRealm {
    @Autowired
    private UserService userService;
    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String usercode=(String) super.getAvailablePrincipal(principals);
        List<UserRole> userRoles = userService.queryUserRolesByUsercode(usercode);
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        if(!userRoles.isEmpty()) {
            for(UserRole role : userRoles) {
                info.addRole(role.getRolecode());
            }
        }
        return info;
    }
    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String usercode= (String)token.getPrincipal();
        List<User> users = userService.queryUsersByUsercode(usercode);
        if (users.isEmpty()) {
            throw new UnknownAccountException();
        } else if(users.size() > 1) {
            throw new DisabledAccountException();
        } else {
            User user = users.get(0);
            // 校验密码
            return new SimpleAuthenticationInfo(user.getUsercode(), user.getPassword(),  getName());
        }
    }


    /*@Override
    public String getName() {
        return "myRealm1";
    }

    @Override
    public boolean supports(AuthenticationToken authenticationToken) {
        return authenticationToken instanceof UsernamePasswordToken;
    }

    @Override
    public AuthenticationInfo getAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username=(String)token.getPrincipal();
        String password=new String((char[])token.getCredentials());
        if(!"liulei".equals(username)){
            throw new UnknownAccountException();
        }
        if(!"123456".equals(password)){
            throw new IncorrectCredentialsException();
        }

        return new SimpleAuthenticationInfo(username, password, getName());
    }*/


}
