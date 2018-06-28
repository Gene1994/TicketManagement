package com.genequ.ticketmanagement.web.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CustomerControllerTest {
    private String authorization = null;
    private String username = "1234";
    private String password = "1234";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    protected WebApplicationContext wac;

    @Before()  //这个方法在每个方法执行之前都会执行一遍
    public void setup() throws Exception{
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();  //初始化MockMvc对象
        //登录
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(" /login")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .requestAttr("username", username)
                .requestAttr("password", password))
                .andExpect(status().is(200))
                .andReturn();
        authorization = result.getResponse().getHeader("Authorization");
    }

//    @Test
//    public void register() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.get("/customer/register"))
//                .andExpect(status().isOk());
//        //.andExpect(MockMvcResultMatchers.content().string("365"));  //测试接口返回内容
//    }

    @Test
    public void success() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/customer/success")
                .header("Authorization", authorization))
                .andExpect(status().is(200));
    }

}