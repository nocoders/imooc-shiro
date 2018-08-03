package com.imooc.shiro.realm;

import com.imooc.dao.UserDao;
import com.imooc.pojo.User;
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

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CustomRealm extends AuthorizingRealm {
    public static void main(String[] args) {
        Md5Hash xiaoming = new Md5Hash("1234567", "xiaoming");
        System.out.println(xiaoming.toString());
    }
    @Resource
    private UserDao userDao;
    //  用于授权
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String username = (String) principalCollection.getPrimaryPrincipal();
//        从数据库中获取角色权限，然后需要授权，不然的话，也是没有权限的。
        Set<String>roles=getRoles(username);
        Set<String>permissions=getPermissionbyusername(username);
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.setRoles(roles);
        simpleAuthorizationInfo.setStringPermissions(permissions);
        return simpleAuthorizationInfo;
    }
    //  通过用户名获取权限数据
    private Set<String> getPermissionbyusername(String username) {
        List<String>list=userDao.getpermissionByUsername(username);
        Set<String> set = new HashSet<String>(list);
        return set;

    }

    //  模拟数据库，通过用户名，获取角色
    private Set<String> getRoles(String username) {
        List<String>list=userDao.getRolesByUsername(username);
        Set<String> set = new HashSet<String>(list);
        return set;
    }

    //  用于认证
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String userName = (String)authenticationToken.getPrincipal();
        String password=getPassword(userName);
        if (password==null){
            return null;
        }
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(userName,password,"customRealm");
//       加的盐
        authenticationInfo.setCredentialsSalt(ByteSource.Util.bytes(userName));

        return authenticationInfo;
    }
    //    登陆获取密码
    private String getPassword(String username){
        User user = userDao.getuserByUsername(username);
        if (user!=null){
            return user.getPassword();
        }
        return null;
    }
}
