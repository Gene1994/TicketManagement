package com.genequ.ticketmanagement.service;

import com.genequ.ticketmanagement.domain.Customer;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface CustomerService {

    /**
     * 注册
     * @param customer
     */
    void register(Customer customer);

    /**
     * 上传头像
     * @param file
     * @throws IOException
     */
    void uploadAvatar(Customer customer, MultipartFile avatar) throws IOException;
}
