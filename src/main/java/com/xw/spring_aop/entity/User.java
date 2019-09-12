package com.xw.spring_aop.entity;

import lombok.Data;

@Data
public class User {
    /**
     * 主键id
     */
    Integer userId;

    /**
     * 姓名
     */
    String username;

    /**
     * 年龄
     */
    Integer age;
}
