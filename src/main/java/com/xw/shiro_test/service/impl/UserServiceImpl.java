package com.xw.shiro_test.service.impl;

import com.xw.shiro_test.mapper.UserMapper;
import com.xw.shiro_test.model.User;
import com.xw.shiro_test.service.UserService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 谢为
 * @since 2019-08-19
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;

//    @Override
//    public List<User> getAllUser() {
//        return userMapper.getAllUser();
//    }

    @Override
    public User getUserByUserName(String username) {
        return userMapper.getUserByUserName(username);
    }

//    @Override
//    public boolean login(String username,String password) {
//        User user = getUserByUserName(username);
//        if(password.equals(user.getPassword())){
//            return true;
//        }else
//            return false;
//    }
}
