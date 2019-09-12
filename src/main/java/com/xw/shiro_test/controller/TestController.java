package com.xw.shiro_test.controller;

import com.alibaba.fastjson.JSONObject;
import com.xw.shiro_test.model.User;
import com.xw.shiro_test.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@RestController
public class TestController {
    @Autowired
    private UserService userService;

    @RequestMapping("/getUserByUsername")
    public User getUserByUsername(@RequestParam("username") String username){
        return userService.getUserByUserName(username);
    }

    @RequestMapping("/welcome")
    public String welconme(){
        return "welcome";
    }

    @RequiresRoles(value = {"admin"})
    @RequestMapping("/addUser")
    public String addUser()throws AuthorizationException {
        return "添加用户";
    }

//    @RequestMapping("/logout")
//    public String logout(HttpServletResponse response,
//                         HttpServletRequest request) throws IOException {
//        response.sendRedirect(request.getContextPath()+"/login.html");
//        return "logout success!";
//    }

    @RequestMapping("/login")
    public String login(@RequestParam("username") String username,
                            @RequestParam("password") String password,
                            HttpServletResponse response,
                            HttpServletRequest request,
                            HttpSession session) throws IOException {
        System.out.println(username+"+++++"+password);
//        JSONObject jsonObject = JSONObject.parseObject(json);
//
//        String username = jsonObject.getString("username");
//        String password = jsonObject.getString("password");
        Subject subject = SecurityUtils.getSubject();
        try {
            if(!subject.isAuthenticated()){
                UsernamePasswordToken token = new UsernamePasswordToken(username,password);
                subject.login(token);
                User user = (User) subject.getPrincipal();
                session.setAttribute("user",user);
            }
            //登录成功，去index页面
            response.sendRedirect(request.getContextPath()+"/index.html");
            return "redirect:/img/1.jpg";
        }catch (Exception e){
            //登录失败，重新登录
            response.sendRedirect(request.getContextPath()+"/login.html");
            return "登录失败，重新登录";
        }
    }
}
