package com.imooc.controller;
/**
 * @Author: linmeng
 * @Description:用户controller
 * @Date: Create in 9:52 2018/7/31
 */
import com.imooc.pojo.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
@Controller
public class UserController {
    @RequestMapping(value = "/subLogin",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    @ResponseBody
    public String subLogin(User user){
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(),user.getPassword());
        try{
            subject.login(token);
        }catch (AuthenticationException e){
            return e.getMessage();
        }
        if (subject.hasRole("admin")){
            System.out.println("有admin权限");
        }else{
            System.out.println("无admin权限");
        }
        if (subject.isPermitted("user:put")){
            System.out.println("还有查询权限");
        }else{
            System.out.println("无查询权限");
        }
        return "登录成功";
    }
    @RequestMapping(value = "/testRole",method = RequestMethod.GET)
    @ResponseBody
    public String testRole(){
        return "testRole success";
    }

    @RequestMapping(value = "/testRole1",method = RequestMethod.GET)
    @ResponseBody
    public String testRole1(){
        return "testRole success";
    }

    @RequestMapping(value = "/testPerms",method = RequestMethod.GET)
    @ResponseBody
    public String testPerms(){
        return "testPerms success";
    }

    @RequestMapping(value = "/testPerms1",method = RequestMethod.GET)
    @ResponseBody
    public String testPerms1(){
        return "testPerms1 success";
    }


}
