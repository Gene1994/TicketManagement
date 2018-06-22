package com.genequ.ticketmanagement.service.impl;

import com.genequ.ticketmanagement.domain.Customer;
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
public class CustomerServiceImplTest {

    @Autowired
    CustomerServiceImpl customerService;

    //已存在用户名
    @Test
    public void userNameExisted() {
        Customer customer = new Customer();
        customer.setId(UUID.randomUUID().toString());
        customer.setUserName("1234");
        customer.setPassword("1");
        customer.setEmail("a@qq.com");
        customer.setCellphone("12345678901");
        try {
            customerService.register(customer);
        } catch (RegisterException e) {
            Assert.assertEquals(1, e.getErrorCode());
        }
    }

    //已存在用户名
    @Test
    public void emailExisted() {
        Customer customer = new Customer();
        customer.setId(UUID.randomUUID().toString());
        customer.setUserName("12343");
        customer.setPassword("1");
        customer.setEmail("123@q.com");
        customer.setCellphone("12345678901");
        try {
            customerService.register(customer);
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