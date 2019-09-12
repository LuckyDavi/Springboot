package com.xw.shiro_test.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.xw.shiro_test.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 谢为
 * @since 2019-08-19
 */
@Mapper
public interface UserMapper  {
//    List<User> getAllUser();

    User getUserByUserName(@Param("username") String username);
}
