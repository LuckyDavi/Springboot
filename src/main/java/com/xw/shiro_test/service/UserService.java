package com.xw.shiro_test.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xw.shiro_test.model.User;


import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 谢为
 * @since 2019-08-19
 */
public interface UserService {
//    public List<User> getAllUser();

    public User getUserByUserName(String username);

//    public boolean login(String username, String password);
}
