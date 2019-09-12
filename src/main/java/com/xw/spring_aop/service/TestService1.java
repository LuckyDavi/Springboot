package com.xw.spring_aop.service;

import com.xw.spring_aop.entity.User;

/**
 * @Description
 * @author XieWei
 */
public interface TestService1 {


    User getUserById(Integer userId);

    String getUsernameById(Integer userId);
}
