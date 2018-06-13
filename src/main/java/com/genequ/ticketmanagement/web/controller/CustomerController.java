package com.genequ.ticketmanagement.web.controller;

import com.genequ.ticketmanagement.domain.Customer;
import com.genequ.ticketmanagement.mapper.CustomerMapper;
import com.genequ.ticketmanagement.service.impl.CustomerServiceImpl;
//import com.genequ.ticketmanagement.service.MailService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.UUID;

//这个注解导致返回字符串
//@RestController
//@ResponseBody
@Slf4j
@Controller
@RequestMapping("/customer")
public class CustomerController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);


    @Autowired
    private CustomerServiceImpl customerServiceImpl;

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

//    @Autowired
//    private MailService mailService;


    @RequestMapping(value = "/login")
    public String login(HttpServletRequest request, HttpServletResponse response) {
//        String jwt = request.getHeader("Authorization");
//        response.addHeader("AvatarUrl", customerServiceImpl.getCustomerByJWT(jwt).getAvatarUrl());
        return "login";
    }

    /**
     * 注册
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String regist(HttpServletRequest request) {
//        CustomerServiceImpl service = new CustomerServiceImpl();
        if (request.getParameter("passwordsignup").equals(request.getParameter("passwordsignup_confirm"))) {
            Customer customer = new Customer();
            customer.setId(UUID.randomUUID().toString());
            customer.setUserName(request.getParameter("usernamesignup"));
//            customer.setPassword(request.getParameter("passwordsignup"));
            //使用BCryptPasswordEncoder管理密码
            customer.setPassword(DigestUtils.md5DigestAsHex((request.getParameter("passwordsignup")).getBytes()));
            customer.setEmail(request.getParameter("emailsignup"));
            customer.setCellphone(request.getParameter("cellphonesignup"));
            customerServiceImpl.regist(customer);
//            confirm("注册成功")；
//            mailService.sendSimpleMail(customer.getEmail(), "恭喜您成功注册TicketManagement","恭喜您成功注册TicketManagement！您的用户名为：" + customer.getUserName());
            return "success";
        } else {
            return "fail";
        }
    }


//    @RequestMapping("/login")
//    public String login(HttpServletRequest request) {
//        String userName = request.getParameter("username");
//        String password = request.getParameter("password");
//        if (checkEmailFormat(userName)) {
//            request.getSession().setAttribute("customer", customerServiceImpl.getCustomerByEmail(userName));
//        } else {
//            request.getSession().setAttribute("customer", customerServiceImpl.getCustomerByUserName(userName));
//        }
//        return customerServiceImpl.login(userName, password);
//    }

    //验证邮箱格式
//    boolean checkEmailFormat(String email) {
//        Pattern emailPattern = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
//        Matcher matcher = emailPattern.matcher(email);
//        if (matcher.find()) {
//            return true;
//        }
//        return false;
//    }

    @RequestMapping("/index")
    public String index() {
        return "index";
    }


    /**
     * 登录成功 将用户头像url传至response header中 返回至index
     * @return
     */
    @RequestMapping("/success")
    public String success(HttpServletRequest request, HttpServletResponse response) {
        return "success";
    }

    @RequestMapping("/fail")
    public String fail() {
        return "fail";
    }

    /**
     * 我的信息
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/myProfile")
    public Customer myProfile(HttpServletRequest request, HttpServletResponse response) {
        Customer customer = customerServiceImpl.getCustomerByJWT(request.getHeader("Authorization "));
        response.setHeader("username", customer.getUserName());
        response.setHeader("email", customer.getEmail());
        response.setHeader("cellphone", customer.getCellphone());
        response.setHeader("avatarUrl",customer.getAvatarUrl());
        return customer;
    }

    /**
     * 上传头像
     * @param file
     * @param request
     * @return
     */
    @RequestMapping("/uploadAvatar")
    public String uploadAvatar(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        Customer customer = customerServiceImpl.getCustomerByJWT(request.getHeader("Authorization"));
//        String contentType = file.getContentType();
        String fileName = new Date().getTime() + "_" + file.getOriginalFilename();//文件名+上传时的时间戳 避免重名
//        String filePath = request.getSession().getServletContext().getRealPath("imgupload/");
        String filePath = "D:\\TicketManagement\\customer\\avatar\\";
        try {
            customerServiceImpl.uploadAvatar(file.getBytes(), filePath, fileName);
        } catch (Exception e) {
        }
        customer.setAvatarUrl(filePath + fileName);
        customerMapper.setAvatarUrl(customer.getId(), filePath + fileName);
        return "success";
    }
}
