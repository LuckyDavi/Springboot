package com.xw.spring_aop.controller;

import com.xw.spring_aop.entity.User;
import com.xw.spring_aop.service.TestService1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/aop")
public class TestController1 {

    @Autowired
    private TestService1 testService1;

    @RequestMapping(value = "/getUserById",method = RequestMethod.POST)
    public User getUserById(@RequestBody User user){
        Integer userId = user.getUserId();
        return testService1.getUserById(userId);
    }
}
