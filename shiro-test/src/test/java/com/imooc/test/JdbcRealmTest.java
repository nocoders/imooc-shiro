package com.imooc.test;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

/**
 * 这是使用默认的sql语句，创建的表和字段必须和shiro源码中的一样，这样局限性太大，我们要使用自己的sql
 */
public class JdbcRealmTest {
        DruidDataSource dataSource=new DruidDataSource();
    {
        dataSource.setUrl("jdbc:mysql://localhost:3306/test");
        dataSource.setUsername("root");
        dataSource.setPassword("root");
    }
    @Test
    public void testAuthenication(){
        JdbcRealm realm = new JdbcRealm();
        realm.setDataSource(dataSource);
//        使用jdbc时，权限开关默认为FALSE，先设置为true，才能访问数据库权限
        realm.setPermissionsLookupEnabled(true);

//        创建sql语句
        String sql="select password from users where username = ?";
        String sql2="select role_name from user_roles where username = ?";
        realm.setUserRolesQuery(sql2);
        realm.setAuthenticationQuery(sql);
        DefaultSecurityManager manager = new DefaultSecurityManager();
        manager.setRealm(realm);
//        主体提交认证请求
        SecurityUtils.setSecurityManager(manager);
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken("xiaoming", "123456");
        subject.login(token);
        System.out.println("subject.isAuthenticated():"+ subject.isAuthenticated());
        subject.checkRoles("admin","user");
//        subject.checkPermission("user:select");
    }
}
