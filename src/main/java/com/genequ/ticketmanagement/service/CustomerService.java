package com.genequ.ticketmanagement.service;

import com.genequ.ticketmanagement.domain.Customer;

import java.io.IOException;

public interface CustomerService {

    /**
     * 注册
     * @param customer
     */
    void regist(Customer customer);

    /**
     * 上传头像
     * @param file 头像文件
     * @param filePath 头像在服务器路径
     * @param fileName 头像在服务器名字
     */
    void uploadAvatar(byte[] file, String filePath, String fileName) throws IOException;
}
