package com.imooc.shiro.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CustomRealm extends AuthorizingRealm {
    public static void main(String[] args) {
        Md5Hash hash = new Md5Hash("123456","Mark");
        System.out.println(hash.toString());
    }
//    暂时不读取数据库，定义一个map作为数据库
    Map<String,String>map=new HashMap();
    {
        map.put("xiaoming","283538989cef48f3d7d8a1c1bdf2008f");
        super.setName("customRealm");
    }
//  用于授权
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String username = (String) principalCollection.getPrimaryPrincipal();
//        从数据库或缓存中获取角色数据
        Set<String>roles=getRoles(username);
        Set<String>permissions=getPermissionbyusername(username);
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.setRoles(roles);
        simpleAuthorizationInfo.setStringPermissions(permissions);
        return simpleAuthorizationInfo;
    }
//  通过用户名获取权限数据
    private Set<String> getPermissionbyusername(String username) {
        HashSet<String> set = new HashSet<String>();
        set.add("user:add");
        set.add("user:delete");
        return set;

    }

    //  模拟数据库，通过用户名，获取角色
    private Set<String> getRoles(String username) {
        HashSet<String> set = new HashSet<String>();
        set.add("admin");
        set.add("user");
        return set;
    }

    //  用于认证
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String userName = (String)authenticationToken.getPrincipal();
        String password=getPassword(userName);
        if (password==null){
            return null;
        }
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo("xiaoming",password,"customRealm");
//       加的盐
        authenticationInfo.setCredentialsSalt(ByteSource.Util.bytes("Mark"));

        return authenticationInfo;
    }
//    工具类 根据用户名获取密码，模拟数据库
    private String getPassword(String username){
        return map.get(username);
    }
}
