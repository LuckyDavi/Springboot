package com.xw.spring_aop.annotation;


import java.lang.annotation.*;

/**
 * @Description 自定义注解
 * @author XieWei
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AopTest {

    String name() default "test1" ;
    int age() default 11;
}
