package com.imooc.filter;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @Author: linmeng
 * @Description:自定义Filter
 * 传递多个角色，只要满足其中的一个角色
 *  AuthorizationFilter:和授权相关
 * @Date: Create in 15:02 2018/8/1
 */
public class RolesFilter extends AuthorizationFilter {

    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {
        Subject subject = getSubject(servletRequest, servletResponse);
        String[] roles=(String[])o;
        if (roles==null || roles.length == 0){
            return true;
        }
        for (String role:roles
             ) {
            if (subject.hasRole(role)){
                return true;
            }

        }
        return false;
    }
}
