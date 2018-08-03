package com.imooc.test;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;

import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.Test;

public class AuthenicationTest {
    SimpleAccountRealm simpleAccountRealm=new SimpleAccountRealm();

    /**
     * 认证前先添加用户，如果给用户添加角色，就可以给用户授权
     */
    @Before
    public void addUser(){
        simpleAccountRealm.addAccount("xia","666666","admin","user");
    }

    /**
     * 登录认证
     */
    @Test
    public void testAuthenication(){
        DefaultSecurityManager manager = new DefaultSecurityManager();
        manager.setRealm(simpleAccountRealm);
//        主体提交认证请求
        SecurityUtils.setSecurityManager(manager);
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken("xia", "666666");
        subject.login(token);
        System.out.println("subject.isAuthenticated():"+ subject.isAuthenticated());
//        subject.logout();
//        System.out.println("subject.isAuthenticated():"+subject.isAuthenticated());
        subject.checkRoles("admin","use");
    }
}
