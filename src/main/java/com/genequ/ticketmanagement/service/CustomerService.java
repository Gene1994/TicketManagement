package com.genequ.ticketmanagement.service;

import com.genequ.ticketmanagement.domain.Customer;
import com.genequ.ticketmanagement.exception.RegisterException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface CustomerService {

    /**
     * 注册
     * @param customer
     */
    boolean register(Customer customer) throws RegisterException;

    /**
     * 上传头像
     * @param customer
     * @param avatar
     * @throws IOException
     */
    boolean uploadAvatar(Customer customer, MultipartFile avatar) throws IOException;

}
