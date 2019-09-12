package com.xw.spring_aop.service.impl;


import com.xw.spring_aop.annotation.AopTest;
import com.xw.spring_aop.entity.User;
import com.xw.spring_aop.service.TestService1;
import org.springframework.stereotype.Service;

@Service
public class TestService1Impl implements TestService1 {

    @AopTest(name = "李四",age = 22)
    @Override
    public User getUserById(Integer userId) {
        User user = new User();
        user.setUsername("张三");
        user.setAge(24);
        user.setUserId(userId);
        return user;
    }

    @Override
    public String getUsernameById(Integer userId) {
        System.out.println("张三");
        return null;
    }
}
