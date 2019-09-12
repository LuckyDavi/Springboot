package com.xw.spring_aop.aspect;

import com.xw.spring_aop.annotation.AopTest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.aspectj.lang.annotation.Aspect;


/**
 * 切面类，注解处理
 */
@Aspect
@Component
public class AspectTset {
    @Pointcut("@annotation(com.xw.spring_aop.annotation.AopTest)")
    public void anon() {
        //切点，不进入该方法
    }

    @Pointcut("execution (* com.xw.spring_aop.service.impl.*.*(..))")
    public void cut1() {
        //切点，不进入该方法
    }

    @Pointcut("execution (* getUsernameById(..))")
    public void cut2() {
        //切点，不进入该方法
    }

    /** "anon() && @annotation(aoptest)" ：   anon()是上面定义的切点，   @annotation(aoptest)用于获取传入注解的参数值  **/
    @After("anon() && @annotation(aoptest)")
    public void after(JoinPoint joinPoint, AopTest aoptest){
        //获取前端传入方法中的参数
        Object args =  joinPoint.getArgs()[0];
        Integer param = (Integer) args;
        System.out.println(param);

        //获取传入注解的参数
        String anonName = aoptest.name();
        int anonAge = aoptest.age();
        System.out.println(anonName+anonAge);
    }

    @Before("cut2()")
    public void before(){
        System.out.println("before");
    }
}
