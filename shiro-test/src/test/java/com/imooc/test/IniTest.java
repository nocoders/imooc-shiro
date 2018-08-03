package com.imooc.test;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

public class IniTest {


    /**
     * 登录认证
     */
    @Test
    public void testAuthenication(){
        IniRealm realm = new IniRealm("classpath:user.ini");
        DefaultSecurityManager manager = new DefaultSecurityManager();
        manager.setRealm(realm);
//        主体提交认证请求
        SecurityUtils.setSecurityManager(manager);
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken("xiaoming", "123456");
        subject.login(token);
        System.out.println("subject.isAuthenticated():"+ subject.isAuthenticated());
        subject.checkRole("admin");
        subject.checkPermission("user:delete");
        subject.checkPermissions("user:delete","user:update");
    }
}
