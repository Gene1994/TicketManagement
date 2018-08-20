package com.genequ.ticketmanagement.aop;

import com.genequ.ticketmanagement.mapper.UserMapper;
import com.genequ.ticketmanagement.util.RedisUtil;
import org.aspectj.lang.annotation.Aspect;
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

    //可以使用Tomcat配置的方式将session存入Redis
    /**
     * 登录后将用户信息存入redis
     * @param joinPoint
     */
//    @After("login()")
//    public void setUserSessionRedis(JoinPoint joinPoint){
//        Object[] args = joinPoint.getArgs();
//        String username = args[0].toString();
//        String password = args[1].toString();
//        User user = userMapper.selectLogin(username, password);
//        Map<String,Object> userMap = new HashMap<>();
//        userMap.put("id", user.getId());
//        userMap.put("username", user.getUsername());
//        userMap.put("password", user.getPassword());
//        userMap.put("email", user.getEmail());
//        userMap.put("phone", user.getPhone());
//        userMap.put("question", user.getQuestion());
//        userMap.put("answer", user.getAnswer());
//        userMap.put("role", user.getRole());
//        userMap.put("createTime", user.getCreateTime());
//        userMap.put("updateTime", user.getUpdateTime());
//        redisUtil.hmset(user.getId().toString(), userMap);
//    }
}
