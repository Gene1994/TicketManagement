package com.genequ.ticketmanagement.service.impl;

import com.genequ.ticketmanagement.pojo.User;
import com.genequ.ticketmanagement.exception.RegisterException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceImplTest {

    @Autowired
    UserServiceImpl customerService;

    //已存在用户名
    @Test
    public void userNameExisted() {
        User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setUserName("1234");
        user.setPassword("1");
        user.setEmail("a@qq.com");
        user.setCellphone("12345678901");
        try {
            customerService.register(user);
        } catch (RegisterException e) {
            Assert.assertEquals(1, e.getErrorCode());
        }
    }

    //已存在用户名
    @Test
    public void emailExisted() {
        User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setUserName("12343");
        user.setPassword("1");
        user.setEmail("123@q.com");
        user.setCellphone("12345678901");
        try {
            customerService.register(user);
        } catch (RegisterException e) {
            Assert.assertEquals(2, e.getErrorCode());
        }
    }

    @Test
    public void loadUserByUsername() {
    }

    @Test
    public void uploadAvatar() {
    }
}