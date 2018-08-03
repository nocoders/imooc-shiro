package com.imooc.test;

import com.imooc.shiro.realm.CustomRealm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

public class CustomerRealmTest {
    @Test
    public void testAuthenication(){
        CustomRealm realm = new CustomRealm();
        DefaultSecurityManager manager = new DefaultSecurityManager();
        manager.setRealm(realm);
//        加密对象
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
//        加密方法名称
        matcher.setHashAlgorithmName("md5");
//        加密次数
        matcher.setHashIterations(1);
//        自定义realm中设置macther对象
        realm.setCredentialsMatcher(matcher);
//        主体提交认证请求
        SecurityUtils.setSecurityManager(manager);
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken("xiaoming", "123456");
        subject.login(token);
        System.out.println("subject.isAuthenticated():"+ subject.isAuthenticated());
        subject.checkRole("admin");
//        subject.checkPermission("user:delete");
//        subject.checkPermissions("user:delete","user:update");
    }
}
