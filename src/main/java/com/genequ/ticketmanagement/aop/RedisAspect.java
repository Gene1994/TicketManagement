package com.genequ.ticketmanagement.aop;

import com.genequ.ticketmanagement.mapper.UserMapper;
import com.genequ.ticketmanagement.util.RedisUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class RedisAspect {

    @Autowired
    private UserMapper userMapper;

    RedisUtil redisUtil = new RedisUtil();

    @Pointcut("execution(public com.genequ.ticketmanagement.controller.portal.UserController login(..))")
    public void login(){}

    @After("login()")
    public void setUserSessionRedis(JoinPoint joinPoint){
        Object[] args = joinPoint.getArgs();
        String username = args[0].toString();
        String userId = userMapper.getIdByUsername(username);

    }
}
